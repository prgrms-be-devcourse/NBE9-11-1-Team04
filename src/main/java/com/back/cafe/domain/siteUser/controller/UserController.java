package com.back.cafe.domain.siteUser.controller;

import com.back.cafe.domain.siteUser.dto.UserDto;
import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.service.UserService;
import com.back.cafe.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "유저 다건 조회")
    public List<UserDto> getUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    @Operation(summary = "유저 단건 조회")
    public UserDto getUser(@PathVariable Long id) {
        return userService.findById(id);
    }


    // 유저 생성 Dto
    public record UserCreateReq(
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

    public record UserCreateRes(
            UserDto user
    ) {
    }

    @PostMapping
    @Transactional
    @Operation(summary = "유저 생성")
    public RsData<UserCreateRes> create(
            @RequestBody @Valid UserCreateReq reqBody
    ) {
        SiteUser siteUser = userService.createUser(reqBody.toEntity());
        return new RsData<>(
                "유저 등록 완료",
                "201-1",
                new UserCreateRes(UserDto.from(siteUser))
        );
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "유저 삭제")
    public RsData<UserDto> deleteUser(
            @PathVariable Long id
    ) {
        UserDto user = userService.findById(id);
        userService.delete(user);

        return new RsData<UserDto>(
                "유저 삭제 완료",
                "200-1",
                user
        );
    }
}
