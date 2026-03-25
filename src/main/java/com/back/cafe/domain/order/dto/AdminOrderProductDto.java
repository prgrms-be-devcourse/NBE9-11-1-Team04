package com.back.cafe.domain.order.dto;

public record AdminOrderProductDto(
        Long productId,
        String productName,
        int quantity
) {
}
