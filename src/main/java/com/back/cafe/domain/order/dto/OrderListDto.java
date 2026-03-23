package com.back.cafe.domain.order.dto;

import java.util.List;

public record OrderListDto(
        List<OrderDto> orderDtos
) {
}
