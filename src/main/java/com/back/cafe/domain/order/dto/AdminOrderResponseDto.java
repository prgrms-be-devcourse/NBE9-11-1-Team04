package com.back.cafe.domain.order.dto;

import java.time.LocalDateTime;
import java.util.List;

public record AdminOrderResponseDto(
        Long id,
        Long userId,
        String email,
        String address,
        int totalPrice,
        LocalDateTime orderedAt,
        String status,
        List<AdminOrderProductDto> orderProducts
) {
}
