package com.back.cafe.domain.siteUser.dto;

import com.back.cafe.domain.siteUser.entity.SiteUser;

public record UserDto(
        Long id,
        String email,
        String address,
        String zipCode
){
    public UserDto(SiteUser siteUser) {
        this(
                siteUser.getId(),
                siteUser.getEmail(),
                siteUser.getAddress(),
                siteUser.getZipCode()
                );
    }
}


