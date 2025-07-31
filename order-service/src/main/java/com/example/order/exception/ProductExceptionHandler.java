package com.example.order.exception;

import com.example.order.util.ErrorStructure;
import com.example.order.util.RestResponseBuilder;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;


    @RestControllerAdvice
    @AllArgsConstructor
    public class ProductExceptionHandler {

        private final RestResponseBuilder responseBuilder;

        @ExceptionHandler(ProductOutOfStockException.class)
        public ResponseEntity<ErrorStructure> handleProductOutOfStockException(ProductOutOfStockException ex) {
            return responseBuilder.error(HttpStatus.BAD_REQUEST, ex.getMessage());
        }
    }

