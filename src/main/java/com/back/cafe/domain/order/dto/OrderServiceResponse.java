package com.back.cafe.domain.order.dto;

import com.back.cafe.domain.order.entity.Order;

public record OrderServiceResponse(Order order, boolean created) {}

