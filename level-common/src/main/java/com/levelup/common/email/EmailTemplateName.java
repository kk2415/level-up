package com.levelup.common.email;

import lombok.Getter;

@Getter
public enum EmailTemplateName {
    AUTHENTICATE_MAIL("email/authenticate-email");

    private final String fileName;

    EmailTemplateName(String fileName) {
        this.fileName = fileName;
    }
}
