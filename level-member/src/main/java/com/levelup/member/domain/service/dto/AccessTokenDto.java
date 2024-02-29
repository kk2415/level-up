package com.levelup.member.domain.service.dto;

import lombok.Getter;

import java.util.Date;

@Getter
public class AccessTokenDto {

    private String email;
    private String token;
    private Date expiration;
    private Date issuedAt;

    protected AccessTokenDto() {}

    private AccessTokenDto(String email, String token, Date expiration, Date issuedAt) {
        this.email = email;
        this.token = token;
        this.expiration = expiration;
        this.issuedAt = issuedAt;
    }

    public static AccessTokenDto of(String email, String token, Date expiration, Date issuedAt) {
        return new AccessTokenDto(email, token, expiration, issuedAt);
    }
}
