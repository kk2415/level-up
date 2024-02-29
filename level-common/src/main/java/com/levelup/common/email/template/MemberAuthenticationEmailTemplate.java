package com.levelup.common.email.template;

import lombok.Getter;

import java.util.Map;

@Getter
public class MemberAuthenticationEmailTemplate extends EmailTemplate {

    private MemberAuthenticationEmailTemplate(final String securityCode) {
        super("email/authenticate-email", Map.of("securityCode", securityCode));
    }

    public static MemberAuthenticationEmailTemplate from(final String securityCode) {
        return new MemberAuthenticationEmailTemplate(securityCode);
    }
}
