package com.levelup.api.util.email;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class EmailStuff {

    private String email;
    private EmailSubject subject;
    private String securityCode;
    private EmailTemplateName templateName;

    protected EmailStuff() {}

    private EmailStuff(String email, EmailSubject subject, String securityCode, EmailTemplateName templateName) {
        this.email = email;
        this.subject = subject;
        this.securityCode = securityCode;
        this.templateName = templateName;
    }

    public static EmailStuff of(String email, EmailSubject subject, String securityCode, EmailTemplateName templateName) {
        return new EmailStuff(email, subject, securityCode, templateName);
    }

    public Map<String, Object> getProps() {
        final Map<String, Object> props = new HashMap<>();

        props.put("email", email);
        props.put("subject", subject);
        props.put("securityCode", securityCode);
        return props;
    }
}
