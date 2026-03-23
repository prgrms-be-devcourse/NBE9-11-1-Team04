package com.back.cafe.domain.siteUser.repository;

import com.back.cafe.domain.siteUser.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser,Long> {
}
