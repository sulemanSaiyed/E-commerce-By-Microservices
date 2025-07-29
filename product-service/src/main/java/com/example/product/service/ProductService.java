package com.example.product.service;

import com.example.product.exception.ProductNotFoundByIdException;
import com.example.product.mapper.ProductMapper;
import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import com.example.product.requestdto.ProductRequest;
import com.example.product.responsedto.ProductResponse;
import com.example.product.util.CustomPage;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductResponse addProduct(ProductRequest productRequest, Long sellerId) {
        Product product = productMapper.mapToProduct(productRequest);

        // Add seller ID to the product before saving
        product.setSellerId(sellerId);
        // Saving the product to the database
        productRepository.save(product);

        return productMapper.mapToProductResponse(product);
    }

    public ProductResponse getProductById(Long productId) {
        return productRepository.findById(productId)
                .map(productMapper::mapToProductResponse)
                .orElseThrow(() -> new ProductNotFoundByIdException("Product not found with ID: " + productId));
    }

    public Page<ProductResponse> getAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size))
                .map(productMapper::mapToProductResponse);
    }

    public CustomPage<ProductResponse> convertToCustomPage(Page<ProductResponse> page) {
        return CustomPage.<ProductResponse>builder()
                .data(page.toList())
                .page(page.getPageable().getPageNumber())
                .size(page.getPageable().getPageSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .build();
    }
}