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

    @Column(nullable = true)
    private String imageUrl;

    public Product(String name, String category, Long price, int stock, String description, String imageUrl) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public void update(String name, String category,Long price, Integer stock, String description, String imageUrl){
        if (name != null) this.name = name;
        if (category != null) this.category = category;
        if (price != null) this.price = price;
        if (stock != null) this.stock = stock;
        if (description != null) this.description = description;
        if (imageUrl != null) this.imageUrl = imageUrl;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("재고 감소량은 1 이상이어야 합니다.");
        }
        if (this.stock < quantity) {
            throw new IllegalArgumentException("재고 부족");
        }
        this.stock -= quantity;
    }

    public void increaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("재고 증가량은 1 이상이어야 합니다.");
        }
        this.stock += quantity;
    }
}
