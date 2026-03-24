package com.back.cafe.domain.siteUser.service;


import com.back.cafe.domain.product.dto.ProductDto;
import com.back.cafe.domain.siteUser.dto.UserDto;
import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto createUser(SiteUser siteUser) {
        userRepository.save(siteUser);
        return UserDto.from(siteUser);
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
