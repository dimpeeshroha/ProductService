package com.capstone.productservice.services;

import com.capstone.productservice.dto.FakeStoreProductDto;
import com.capstone.productservice.exceptions.InvalidProductIdException;
import com.capstone.productservice.models.Category;
import com.capstone.productservice.models.Product;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // This API returns a single product from FakeStore based on Product Id
    @Override
    public Product getProductById(Long id) throws InvalidProductIdException {
       FakeStoreProductDto fakeStoreProductDto =  restTemplate.getForObject("https://fakestoreapi.com/products/"+id, FakeStoreProductDto.class);
       if(fakeStoreProductDto == null){
           throw new InvalidProductIdException("Invalid Product Id passed");
       }
       return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);

    }

    @Override
    public List<Product> getAllProducts() {

        FakeStoreProductDto[] fakeStoreProducts = restTemplate.getForObject("https://fakestoreapi.com/products/", FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProducts){
            products.add(convertFakeStoreProductDtoToProduct(fakeStoreProductDto));
        }
        return products;
    }

    @Override
    public Product createProduct(Product product) {
        FakeStoreProductDto fakeStoreProductDtoReq = convertProductToFakeStorePRoductDto(product);
        FakeStoreProductDto fakeStoreProductDtoRes = restTemplate.postForObject("https://fakestoreapi.com/products", fakeStoreProductDtoReq, FakeStoreProductDto.class);
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDtoRes);

    }
    //explain below method replaceProduct

    public Product replaceProduct(Long id, Product product){

        RequestCallback requestCallback = restTemplate.httpEntityCallback(product, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto fakeStoreProductDto = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.PUT, requestCallback, responseExtractor);
        return convertFakeStoreProductDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }




    private Product convertFakeStoreProductDtoToProduct(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setTitle(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImage(fakeStoreProductDto.getImage());
        product.setPrice(fakeStoreProductDto.getPrice());
        Category category = new Category();
        category.setTitle(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }
    private FakeStoreProductDto convertProductToFakeStorePRoductDto(Product product){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImage());
        fakeStoreProductDto.setCategory(product.getCategory().getTitle());
        return fakeStoreProductDto;
    }
}
