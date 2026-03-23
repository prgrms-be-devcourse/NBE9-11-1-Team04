package com.back.cafe.order.dto;

import com.back.cafe.order.entity.Order;
import com.back.cafe.order.entity.OrderProduct;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDto(
    Long id,
    Long userId,
    int totalPrice,
    LocalDateTime ordered_at,
    String status,
    List<OrderProduct> orderProducts
) {
    public OrderDto(Order order) {
        this(
                order.getId(),
                order.getUserId(),
                order.getTotalPrice(),
                order.getModified_at(),
                order.getStatus(),
                order.getOrderProducts()
        );
    }
}
