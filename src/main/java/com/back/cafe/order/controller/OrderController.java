package com.back.cafe.order.controller;

import com.back.cafe.order.dto.OrderDto;
import com.back.cafe.order.dto.OrderProductDto;
import com.back.cafe.order.entity.Order;
import com.back.cafe.order.service.OrderService;
import com.back.cafe.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    record OrderWriteReqBody(
            @NotEmpty
            List<OrderProductDto> orderProductRequests
    ){}

    record OrderWriteResBody(
            Order order
    ){}

    // 주문 생성
    @PostMapping("/user/{userId}")
    public RsData<OrderDto> createOrder(
            @PathVariable Long userId,
            @RequestBody @Valid OrderWriteReqBody reqBody
    ){
        Order order = orderService.createOrder(userId, reqBody.orderProductRequests);
        return new RsData<>(
                "%d번 주문이 생성되었습니다.".formatted(order.getId()),
                "201-1",
                new OrderDto(order)
        );
    }
}
