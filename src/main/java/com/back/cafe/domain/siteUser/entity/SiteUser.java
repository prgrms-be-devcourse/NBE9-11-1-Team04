package com.back.cafe.domain.siteUser.entity;


import com.back.cafe.global.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@Getter
public class SiteUser extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String zipCode;

    public SiteUser(String email, String address, String zipCode){
        this.email=email;
        this.address=address;
        this.zipCode=zipCode;
    }

    public void update(String address, String zipCode) {
        this.address = address;
        this.zipCode = zipCode;
    }
}
