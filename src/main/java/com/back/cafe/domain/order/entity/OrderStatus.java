package com.back.cafe.domain.order.entity;

/**
 * 주문 상태값 목록
 * - PENDING   : 주문 대기
 * - SHIPPED   : 배송 중
 * - DELIVERED : 배송 완료
 * - CANCELLED : 주문 취소
 */
public enum OrderStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}