// 파일명: UserCreateDto.java (또는 별도 파일 없이 관리 가능)
package com.back.cafe.domain.siteUser.dto;

import jakarta.validation.constraints.NotBlank;

public class UserCreateDto {

    public record UserCreateReqBody(
            String email,
            String address,
            String zipCode
    ) {}

    public record UserCreateResBody(
            UserDto user
    ) {}
}