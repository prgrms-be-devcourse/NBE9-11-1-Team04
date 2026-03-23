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
    public RsData<OrderDto> createUpdateOrder(
            @PathVariable Long userId,
            @RequestBody @Valid OrderWriteReqBody reqBody
    ){
        Order order = orderService.doOrder(userId, reqBody.orderProductRequests);
        String msg = order.getCreated_at()==order.getModified_at()?"번 주문이 생성되었습니다.":"번 추가주문이 완료되었습니다.";
        return new RsData<>(
                "%d%s".formatted(order.getId(),msg),
                "201-1",
                new OrderDto(order)
        );
    }

}
