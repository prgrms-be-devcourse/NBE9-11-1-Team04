package com.back.cafe.domain.siteUser.controller;

import com.back.cafe.domain.siteUser.dto.UserDto;
import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.service.UserService;
import com.back.cafe.global.rsData.RsData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    @Transactional(readOnly = true)
    public List<UserDto> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public UserDto getUser(@PathVariable Long id) {
        return userService.findById(id);
    }


    public record UserCreateReqBody(
            @NotBlank(message = "이메일은 필수입니다.") String email,
            @NotBlank(message = "주소는 필수입니다.") String address,
            @NotBlank(message = "우편번호는 필수입니다.") String zipCode
    ) {
        public SiteUser toEntity() {
            return SiteUser.builder()
                    .email(this.email)
                    .address(this.address)
                    .zipCode(this.zipCode)
                    .build();
        }
    }

    public record UserCreateResBody(
            UserDto user
    ) {
    }

    @PostMapping
    @Transactional
    public RsData<UserCreateResBody> create(
            @RequestBody @Valid UserCreateReqBody reqBody
    ) {
        SiteUser siteUser = userService.createUser(reqBody.toEntity());
        return new RsData<>(
                "유저 등록 완료",
                "201-1",
                new UserCreateResBody(UserDto.from(siteUser))
        );
    }

}
