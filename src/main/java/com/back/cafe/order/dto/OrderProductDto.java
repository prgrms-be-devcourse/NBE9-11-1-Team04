package com.back.cafe.order.dto;

// OrderProductDto : 주문 생성 Request DTO -> OrderReqBody에서 사용
public record OrderProductDto(
        Long productId,
        int quantity
) {}
