package com.back.cafe.domain.order.controller;

import com.back.cafe.domain.order.dto.OrderDto;
import com.back.cafe.domain.order.dto.OrderProductDto;
import com.back.cafe.domain.order.entity.Order;
import com.back.cafe.domain.order.service.OrderService;
import com.back.cafe.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    record OrderWriteReqBody(
            @NotEmpty
            List<OrderProductDto> orderProductRequests
    ){}

    // 주문 생성
    @PostMapping("/user/{userId}")
    public RsData<OrderDto> createOrder(
            @PathVariable Long userId,
            @RequestBody @Valid OrderWriteReqBody reqBody
    ){
        Optional<Order> existOrder = orderService.findOrder(userId);
        if(existOrder.isPresent()){//&&existOrder.get().getCreated_at() == LocalDateTime.now()
             Order order = orderService.modifyOrder(userId, reqBody.orderProductRequests);
             return new RsData<>(
                    "%d번 추가주문이 완료되었습니다.".formatted(order.getId()),
                    "201-1",
                    new OrderDto(order)
            );
        }
        Order order = orderService.createOrder(userId, reqBody.orderProductRequests);
        return new RsData<>(
                "%d번 주문이 생성되었습니다.".formatted(order.getId()),
                "201-1",
                new OrderDto(order)
        );
    }

}
