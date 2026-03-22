package com.back.cafe.domain.product.entity;

import com.back.cafe.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(nullable = false)
    private String name;

    private String category;

    private Long price;

    private int stock; // 재고

    @Column(columnDefinition = "TEXT")
    private String description; // 상품 설명

    public Product(String name, String category, Long price, int stock, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
}
