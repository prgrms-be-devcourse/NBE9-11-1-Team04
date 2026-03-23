package com.back.cafe.domain.product.dto;

import com.back.cafe.domain.product.entity.Product;
import java.time.LocalDateTime;

public record ProductDto(
        Long id,
        String name,
        Long price,
        String category,
        int stock,
        String description,
        String imageUrl,
        LocalDateTime created_at,
        LocalDateTime modified_at
) {

    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getStock(),
                product.getDescription(),
                product.getImageUrl(),
                product.getCreated_at(),
                product.getModified_at()
        );
    }
}