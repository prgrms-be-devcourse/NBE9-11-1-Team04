package com.back.cafe.domain.siteUser.controller;

import com.back.cafe.domain.siteUser.dto.UserCreateDto;
import com.back.cafe.domain.siteUser.dto.UserDto;
import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.service.UserService;
import com.back.cafe.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public RsData<UserCreateDto.UserCreateResBody> create(
            @RequestBody @Valid UserCreateDto.UserCreateReqBody reqBody
    ) {
        SiteUser siteUser = userService.createUser(reqBody.email(), reqBody.address(), reqBody.zipCode());

        return new RsData<>(
                "201-1",
                "유저 등록 완료",
                new UserCreateDto.UserCreateResBody(new UserDto(siteUser))
        );
    }
}
