package com.back.cafe.domain.siteUser.controller;

import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.siteUser.dto.UserCreateDto;
import com.back.cafe.domain.siteUser.dto.UserDto;
import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.service.UserService;
import com.back.cafe.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
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
    public List<UserDto> getUsers(){return userService.findAll();}

    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public UserDto getUser(@PathVariable Long id){
        return userService.findById(id);
    }

    @PostMapping
    public RsData<UserCreateDto.UserCreateResBody> create(
            @RequestBody @Valid UserCreateDto.UserCreateReqBody reqBody
    ) {
        SiteUser siteUser = userService.createUser(reqBody.email(), reqBody.address(), reqBody.zipCode());
        return new RsData<>(
                "유저 등록 완료",
                "201-1",
                new UserCreateDto.UserCreateResBody(UserDto.from(siteUser))
        );
    }

}
