package com.back.cafe.domain.order.dto;

/**
 * 관리자 주문 상태 변경 요청 DTO
 */
public class OrderStatusUpdateRequest {

    /**
     * 변경할 주문 상태값
     */
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}