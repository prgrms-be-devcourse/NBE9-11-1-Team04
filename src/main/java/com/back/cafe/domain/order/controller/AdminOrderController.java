package com.back.cafe.domain.order.controller;

import com.back.cafe.domain.order.dto.OrderStatusUpdateRequest;
import com.back.cafe.domain.order.service.AdminOrderService;
import com.back.cafe.global.rsData.RsData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<RsData<Void>> cancelOrder(@PathVariable Long orderId) {
        adminOrderService.cancelOrder(orderId);

        RsData<Void> rsData = new RsData<>(
                "주문이 취소되었습니다.",
                "200-1"
        );

        return ResponseEntity
                .status(rsData.getStatusCode())
                .body(rsData);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<RsData<Void>> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody OrderStatusUpdateRequest request
    ) {
        adminOrderService.updateOrderStatus(orderId, request.getStatus());

        RsData<Void> rsData = new RsData<>(
                "주문 상태가 변경되었습니다.",
                "200-1"
        );

        return ResponseEntity
                .status(rsData.getStatusCode())
                .body(rsData);
    }
}