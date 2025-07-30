package com.example.product.controller;

import com.example.product.requestdto.ProductRequest;
import com.example.product.responsedto.ProductResponse;
import com.example.product.service.ProductService;
import com.example.product.util.CustomPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${app.base-url}")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/products")
    public ResponseEntity<ProductResponse> addProduct(@RequestBody ProductRequest productRequest, @RequestParam Long sellerId) {
        ProductResponse productResponse = productService.addProduct(productRequest, sellerId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productResponse);
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        ProductResponse productResponse = productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(productResponse);
    }

    @GetMapping("/products")
    public ResponseEntity<CustomPage<ProductResponse>> getAllProducts(@RequestParam int page, @RequestParam int size) {
        Page<ProductResponse> pageResponse = productService.getAllProducts(page, size);
        CustomPage<ProductResponse> responses = productService.convertToCustomPage(pageResponse);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(responses);
    }
    @PutMapping("/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.updateProduct(id, productRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .build();
    }
}
