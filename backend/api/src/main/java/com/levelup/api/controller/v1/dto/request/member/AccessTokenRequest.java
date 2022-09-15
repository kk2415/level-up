package com.levelup.api.controller.v1.dto.request.member;

import lombok.Getter;

import java.util.Date;

@Getter
public class AccessTokenRequest {

    private Long memberId;
    private String token;
    private Date expiration;
    private Date issuedAt;

    protected AccessTokenRequest() {}

    private AccessTokenRequest(Long memberId, String token, Date expiration, Date issuedAt) {
        this.memberId = memberId;
        this.token = token;
        this.expiration = expiration;
        this.issuedAt = issuedAt;
    }

    public static AccessTokenRequest of(Long memberId, String token, Date expiration, Date issuedAt) {
        return new AccessTokenRequest(memberId, token, expiration, issuedAt);
    }
}
