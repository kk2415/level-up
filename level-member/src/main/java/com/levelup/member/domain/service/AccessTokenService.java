package com.levelup.member.domain.service;

import com.levelup.member.util.jwt.TokenProvider;
import com.levelup.member.domain.service.dto.AccessTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final TokenProvider tokenProvider;

    public AccessTokenDto create(AccessTokenDto dto) {
        String token = tokenProvider.createAccessToken(dto.getEmail());

        return AccessTokenDto.of(
                dto.getEmail(),
                token,
                tokenProvider.getExpiration(token),
                tokenProvider.getIssuedAt(token));
    }
}
