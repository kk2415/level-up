package com.levelup.api.controller.v1.dto.response.member;

import com.levelup.member.domain.service.dto.AccessTokenDto;
import lombok.Getter;

import java.util.Date;

@Getter
public class AccessTokenResponse {

    private String email;
    private String token;
    private Date expiration;
    private Date issuedAt;

    protected AccessTokenResponse() {}

    private AccessTokenResponse(String email, String token, Date expiration, Date issuedAt) {
        this.email = email;
        this.token = token;
        this.expiration = expiration;
        this.issuedAt = issuedAt;
    }

    public static AccessTokenResponse of(String email, String token, Date expiration, Date issuedAt) {
        return new AccessTokenResponse(email, token, expiration, issuedAt);
    }

    public static AccessTokenResponse from(AccessTokenDto dto) {
        return AccessTokenResponse.of(dto.getEmail(), dto.getToken(), dto.getExpiration(), dto.getIssuedAt());
    }
}
