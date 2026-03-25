package com.back.cafe.domain.product.dto.ProductRequestDto;

import com.back.cafe.domain.product.dto.ServiceDto.ServiceModifyProductDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductModifyRequest(
        @NotBlank String name,
        @NotBlank String category,
        @NotNull Long price,
        @NotNull Integer stock,
        @NotBlank String description,
        @NotBlank String imageUrl
) {
    public ServiceModifyProductDto toServiceDto(){
        return new ServiceModifyProductDto(
                name,
                category,
                price,
                stock,
                description,
                imageUrl
        );
    }

}

