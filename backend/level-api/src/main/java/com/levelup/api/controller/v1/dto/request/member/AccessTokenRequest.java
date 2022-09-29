package com.levelup.api.controller.v1.dto.request.member;

import com.levelup.member.domain.service.dto.AccessTokenDto;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
public class AccessTokenRequest {

    @NotNull
    private String email;

    @NotNull
    private String token;

    @NotNull
    private Date expiration;

    @NotNull
    private Date issuedAt;

    protected AccessTokenRequest() {}

    private AccessTokenRequest(String email, String token, Date expiration, Date issuedAt) {
        this.email = email;
        this.token = token;
        this.expiration = expiration;
        this.issuedAt = issuedAt;
    }

    public static AccessTokenRequest of(String email, String token, Date expiration, Date issuedAt) {
        return new AccessTokenRequest(email, token, expiration, issuedAt);
    }

    public AccessTokenDto toDto() {
        return AccessTokenDto.of(this.email, this.token, this.expiration, this.issuedAt);
    }
}
