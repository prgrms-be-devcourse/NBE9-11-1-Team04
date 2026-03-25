package com.back.cafe.domain.product.dto.ServiceDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ServiceCreateProductDto(
        @NotBlank String name,
        @NotBlank String category,
        @NotNull Long price,
        @NotNull Integer stock,
        @NotBlank String description,
        @NotBlank String imageUrl
) {
}