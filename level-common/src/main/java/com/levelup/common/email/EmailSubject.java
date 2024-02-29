package com.levelup.common.email;

import lombok.Getter;

@Getter
public enum EmailSubject {

    AUTHENTICATE_MAIL("[레벨업] 이메일 인증코드");

    private String subject;

    EmailSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return subject;
    }
}
