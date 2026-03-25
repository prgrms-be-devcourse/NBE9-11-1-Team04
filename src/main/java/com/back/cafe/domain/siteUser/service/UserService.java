package com.back.cafe.domain.siteUser.service;


import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.siteUser.controller.UserController;
import com.back.cafe.domain.siteUser.dto.UserDto;
import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserController.UserServiceResponse upsert(UserController.UserCreateReq dto) {
        Optional<SiteUser> User = userRepository.findByEmail(dto.email());

        boolean isCreated = User.isEmpty();

        SiteUser siteUser = User.map(existingUser -> {
            // 기존 유저가 있으면 업데이트
            existingUser.update(dto.address(), dto.zipCode());
            return existingUser;
        }).orElseGet(() -> {
            // 없으면 새로운 유저 생성
            return userRepository.save(dto.toEntity());
        });

        return new UserController.UserServiceResponse(UserDto.from(siteUser), isCreated);
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(UserDto::from)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::from)
                .orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다. ID:"+id));
    }

    public UserDto delete(Long id) {
        SiteUser deleteUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다.ID:"+id));;
        userRepository.delete(deleteUser);
        return UserDto.from(deleteUser);
    }
}
