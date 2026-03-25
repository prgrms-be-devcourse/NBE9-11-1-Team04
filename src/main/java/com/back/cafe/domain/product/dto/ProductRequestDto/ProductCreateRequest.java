package com.back.cafe.domain.product.dto.ProductRequestDto;

import jakarta.validation.constraints.NotBlank;

import com.back.cafe.domain.product.dto.ServiceDto.ServiceCreateProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequest(
        @NotBlank String name,
        @NotBlank String category,
        @NotNull Long price,
        @NotNull Integer stock,
        @NotBlank String description,
        @NotBlank String imageUrl
) {
    public ServiceCreateProductDto toServiceDto(){
        return new ServiceCreateProductDto(
                name,
                category,
                price,
                stock,
                description,
                imageUrl
        );
    }
}