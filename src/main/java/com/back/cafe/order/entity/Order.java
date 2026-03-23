package com.back.cafe.order.entity;

import com.back.cafe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "orders") // order는 예약어이므로 이름 변경
public class Order extends BaseEntity {
    private Long userId;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE},
            orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<OrderProduct> orderProducts = new ArrayList<>();

    private String status;

    private int totalPrice;

    public Order(Long userId){
        this.userId = userId;
        this.status = "order-completed"; // default: 주문 완료
    }

    public void addOrderProduct(OrderProduct orderProduct){
        this.orderProducts.add(orderProduct);
    }
}
