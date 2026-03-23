package com.back.cafe.domain.order.entity;

import com.back.cafe.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderProduct extends BaseEntity {
    @Column(name = "order_id")
    private Long orderId;
    private Long productId;
    private int quantity;

    public OrderProduct(Long productId, int quantity){
        this.productId = productId;
        this.quantity = quantity;
    }
}
