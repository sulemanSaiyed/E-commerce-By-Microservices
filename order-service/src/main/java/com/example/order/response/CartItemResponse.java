package com.example.order.response;

import com.example.order.model.Product;
import lombok.Data;

@Data
public class CartItemResponse {
    private Long id;
    private int quantity;
    private boolean ordered;
    private Product product;
}