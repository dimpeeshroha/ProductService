package com.capstone.productservice.advices;

import com.capstone.productservice.dto.ArithmeticExceptionDto;
import com.capstone.productservice.dto.ExceptionDto;
import com.capstone.productservice.exceptions.InvalidProductIdException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(ArithmeticException.class)
    public ResponseEntity<ArithmeticExceptionDto> handleArithmeticException(){
        ArithmeticExceptionDto arithmeticExceptionDto = new ArithmeticExceptionDto();
        arithmeticExceptionDto.setMessage("Something went wrong");
        arithmeticExceptionDto.setDetails("arithmetic exception");
        return new ResponseEntity<>(arithmeticExceptionDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidProductIdException.class)
    public ResponseEntity<ExceptionDto> handleInvalidProductIdException(){
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage("Invalid Product Id, please retry with a valid product id");
        return new ResponseEntity<>(exceptionDto, HttpStatus.BAD_REQUEST);
    }
}
