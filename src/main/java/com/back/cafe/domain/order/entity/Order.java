package com.back.cafe.domain.order.entity;

import com.back.cafe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문 엔티티
 * - 주문한 사용자 정보
 * - 주문 상품 목록
 * - 주문 상태
 * - 총 가격
 * 를 관리한다.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders") // order는 예약어이므로 orders 사용
public class Order extends BaseEntity {

    private Long userId;

    /**
     * 주문에 포함된 상품 목록
     */
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    /**
     * 주문 상태
     * 현재는 문자열로 저장하되, OrderStatus enum 기준으로 관리한다.
     */
    private String status;

    /**
     * 주문 총 금액
     */
    private int totalPrice;

    /**
     * 주문 생성 시 기본 상태는 PENDING으로 시작한다.
     *
     * @param userId 주문한 사용자 ID
     */
    public Order(Long userId) {
        this.userId = userId;
        this.status = OrderStatus.PENDING.name();
    }

    /**
     * 주문 상품을 목록에 추가한다.
     *
     * @param orderProduct 주문 상품 객체
     */
    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
    }
}