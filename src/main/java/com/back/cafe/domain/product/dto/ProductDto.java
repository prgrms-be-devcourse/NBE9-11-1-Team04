package com.back.cafe.domain.product.dto;

import com.back.cafe.domain.product.entity.Product;

public record ProductDto(
        Long id,
        String name,
        Long price,
        String category,
        int stock,
        String description
) {

    public static ProductDto from(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getStock(),
                product.getDescription()
        );
    }
}