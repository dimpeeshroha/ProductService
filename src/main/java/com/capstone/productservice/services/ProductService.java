package com.capstone.productservice.services;

import com.capstone.productservice.dto.FakeStoreProductDto;
import com.capstone.productservice.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
     Product getProductById(Long id);
     List<Product> getAllProducts();
     Product createProduct(Product product);
     Product updateProduct(Product product);
}
