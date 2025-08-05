package com.example.order.response;


import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private LocalDate orderDate;
    private String status;
    private List<CartItemResponse> orderItems;
    private BigDecimal deliveryCost;
    private BigDecimal totalAmount;
    private BigDecimal totalPayableAmount;
    private String paymentMethod;
    private Long customerId;
    private String deliveryAddress;
}