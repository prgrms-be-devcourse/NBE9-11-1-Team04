package com.back.cafe.domain.siteUser.repository;

import com.back.cafe.domain.siteUser.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser,Long> {
    Optional<SiteUser> findByEmail(String email);
}
