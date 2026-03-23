package com.back.cafe.domain.siteUser.service;


import com.back.cafe.domain.siteUser.entity.SiteUser;
import com.back.cafe.domain.siteUser.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public SiteUser createUser(String email, String address, String zipCode) {
        SiteUser siteUser = new SiteUser(email,address,zipCode);
        userRepository.save(siteUser);
        return siteUser;
    }
}
