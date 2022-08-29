package com.levelup.api.util.jwt;

import lombok.Getter;

import java.util.Date;

@Getter
public class AccessToken {

    private String token;
    private Date expiration;
    private Date issuedAt;

    protected AccessToken() {}

    private AccessToken(String token, Date expiration, Date issuedAt) {
        this.token = token;
        this.expiration = expiration;
        this.issuedAt = issuedAt;
    }

    public static AccessToken of(String token, Date expiration, Date issuedAt) {
        return new AccessToken(token, expiration, issuedAt);
    }
}
