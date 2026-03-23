package com.back.cafe.domain.order.controller;

import com.back.cafe.domain.order.dto.OrderStatusUpdateRequest;
import com.back.cafe.domain.order.service.AdminOrderService;
import com.back.cafe.global.rsData.RsData;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 관리자 주문 상태 제어 API를 담당하는 컨트롤러
 * - 주문 취소
 * - 주문 상태 변경
 */
@RestController
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    /**
     * 관리자 주문 취소 API
     *
     * @param orderId 취소할 주문 ID
     * @return 처리 결과 응답
     */
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

    /**
     * 관리자 주문 상태 변경 API
     *
     * @param orderId 상태를 변경할 주문 ID
     * @param request 변경할 상태값 요청 DTO
     * @return 처리 결과 응답
     */
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