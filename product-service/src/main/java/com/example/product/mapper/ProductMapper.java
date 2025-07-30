package com.example.product.mapper;

import com.example.product.model.Product;
import com.example.product.requestdto.ProductRequest;
import com.example.product.responsedto.ProductResponse;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public class ProductMapper {
    /**
     * Converts a ProductRequest to a Product entity.
     *
     * @param productRequest the ProductRequest object containing product data
     * @return the corresponding Product entity
     */
    Product mapToProduct(ProductRequest productRequest);

    /**
     * Updates an existing Product entity with data from a ProductRequest.
     *
    // * @param productRequest the ProductRequest containing new product data
    // * @param product the Product entity to update
     */
    void mapToProduct(ProductRequest productRequest, @MappingTarget Product product);

    /**
     * Converts a Product entity to a ProductResponse DTO.
     *
   //  * @param product the Product entity to be converted
    // * @return the corresponding ProductResponse DTO
     */
    ProductResponse mapToProductResponse(Product product);
}
