package com.example.order.service;
import com.example.order.exception.ProductOutOfStockException;
import com.example.order.integration.ProductClient;
import com.example.order.mapper.CartItemMapper;
import com.example.order.mapper.OrderMapper;
import com.example.order.model.CartItem;
import com.example.order.model.Order;
import com.example.order.model.Product;
import com.example.order.repository.CartItemRepository;
import com.example.order.repository.OrderRepository;
import com.example.order.response.CartItemResponse;
import com.example.order.response.OrderResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final CartItemMapper cartItemMapper;

    // TODO: The Logic should be updated to validate the user's ownership and then create the order.

    public OrderResponse createOrder() {
        List<CartItem> cartItems = cartItemRepository.findAllByOrdered(false);

        /* Extracting the total amount from the cart items
         * */
        BigDecimal totalAmount = ExtractTotalAmountFromItemList(cartItems);
        BigDecimal deliveryCost = new BigDecimal("10.00"); // Placeholder for actual delivery cost calculation
        Long customerId = 1L; // Placeholder for actual customer ID

        // Creating the order object and saving it to the database.
        Order order = createOrderObject(totalAmount, deliveryCost, customerId, cartItems);
        orderRepository.save(order);

        // Updating the cart items as ordered.
        UpdateCartItemAsOrdered(cartItems, order);

        // creating CartItemResponses from the cart items.
        List<CartItemResponse> cartItemResponses = mapToCartItemResponseList(cartItems);

        // returning the OrderResponse object.
        return getOrderResponse(order, cartItemResponses);
    }

    private OrderResponse getOrderResponse(Order order, List<CartItemResponse> cartItemResponses) {
        OrderResponse orderResponse = orderMapper.mapToOrderResponse(order);
        orderResponse.setOrderItems(cartItemResponses);
        return orderResponse;
    }

    private List<CartItemResponse> mapToCartItemResponseList(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> {
                    Product product = productClient.getProductById(item.getProductId());
                    CartItemResponse response = cartItemMapper.mapToCartItemResponse(item);
                    response.setProduct(product);
                    return response;
                })
                .toList();
    }

    private void UpdateCartItemAsOrdered(List<CartItem> cartItems, Order order) {
        cartItems.forEach(item -> {
            item.setOrdered(true);
            item.setOrder(order);
        });
        cartItemRepository.saveAll(cartItems);
    }

    private BigDecimal ExtractTotalAmountFromItemList(List<CartItem> cartItems) {

        return cartItems.stream().map(item -> {
            Product product = productClient.getProductById(item.getProductId());

            if (product == null || product.getStock() < item.getQuantity()) {
                throw new ProductOutOfStockException("Invalid product or insufficient stock");
            }


            return BigDecimal.valueOf(product.getPrice())
                    .multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order createOrderObject(BigDecimal totalAmount, BigDecimal deliveryCost, Long customerId, List<CartItem> cartItems) {
        return Order.builder()
                .orderDate(LocalDate.now())
                .status("Pending")
                .totalAmount(totalAmount)
                .paymentMethod("Credit Card")
                .deliveryAddress("123 Main St")
                .deliveryCost(deliveryCost)
                .customerId(customerId)
                .orderItems(cartItems)
                .totalPayableAmount(totalAmount.add(deliveryCost))
                .build();
    }}