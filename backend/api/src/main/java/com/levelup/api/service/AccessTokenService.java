package com.levelup.api.service;

import com.levelup.api.controller.v1.dto.request.member.AccessTokenRequest;
import com.levelup.api.util.jwt.AccessToken;
import com.levelup.api.util.jwt.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final TokenProvider tokenProvider;

    public AccessToken getAccessToken(AccessTokenRequest accessTokenRequest) {
        String token = tokenProvider.createAccessToken(accessTokenRequest.getMemberId());
        Claims body = tokenProvider.getBody(token);

        return AccessToken.of(token, body.getExpiration(), body.getIssuedAt());
    }
}
