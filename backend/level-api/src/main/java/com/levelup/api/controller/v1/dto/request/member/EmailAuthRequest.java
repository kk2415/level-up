package com.levelup.api.controller.v1.dto.request.member;

import com.levelup.member.domain.service.dto.EmailAuthDto;
import com.levelup.member.domain.entity.EmailAuth;
import com.levelup.member.domain.entity.EmailAuthType;
import com.levelup.member.domain.entity.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class EmailAuthRequest {

    private String securityCode;
    private EmailAuthType authType;

    protected EmailAuthRequest() {}

    private EmailAuthRequest(String securityCode, EmailAuthType authType) {
        this.securityCode = securityCode;
        this.authType = authType;
    }

    public static EmailAuthRequest of(String securityCode, EmailAuthType authType) {
        return new EmailAuthRequest(securityCode, authType);
    }

    public EmailAuthDto toDto() {
        return EmailAuthDto.of(authType, securityCode, false);
    }

    public EmailAuth toEntity(Member member) {
        return EmailAuth.builder()
                .member(member)
                .authType(authType)
                .email(member.getEmail())
                .securityCode(EmailAuth.createSecurityCode())
                .isAuthenticated(false)
                .receivedDate(LocalDateTime.now())
                .expireDate(LocalDateTime.now().plusMinutes(1))
                .build();
    }
}
