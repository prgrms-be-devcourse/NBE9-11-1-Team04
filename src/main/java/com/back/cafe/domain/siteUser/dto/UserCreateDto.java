// 파일명: UserCreateDto.java (또는 별도 파일 없이 관리 가능)
package com.back.cafe.domain.siteUser.dto;

import jakarta.validation.constraints.NotBlank;

public class UserCreateDto {

    public record UserCreateReqBody(
            @NotBlank(message = "이메일은 필수입니다.") String email,
            @NotBlank(message = "주소는 필수입니다.") String address,
            @NotBlank(message = "우편번호는 필수입니다.") String zipCode
    ) {}

    public record UserCreateResBody(
            UserDto user
    ) {}
}