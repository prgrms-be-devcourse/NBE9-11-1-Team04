package com.back.cafe.domain.siteUser.dto;

import com.back.cafe.domain.siteUser.entity.SiteUser;

public record UserDto(
        Long id,
        String email,
        String address,
        String zipCode
) {
    public static UserDto from(SiteUser siteUser) {
        return new UserDto(
                siteUser.getId(),
                siteUser.getEmail(),
                siteUser.getAddress(),
                siteUser.getZipCode()
        );
    }

    public SiteUser toEntity() {
        return SiteUser.builder()
                .email(this.email)
                .address(this.address)
                .zipCode(this.zipCode)
                .build();
    }
}


