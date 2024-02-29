package com.levelup.common.email.service;

import com.levelup.common.email.EmailSubject;
import com.levelup.common.email.sender.EmailSender;
import com.levelup.common.email.template.EmailTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import static java.util.Locale.KOREAN;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final EmailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public void send(String receiver, EmailSubject subject, EmailTemplate template) {
        String body = templateEngine.process(template.getViewPath(), new Context(KOREAN, template.getModel()));

        mailSender.send(receiver, subject.toString(), body);
    }

    public void send(String receiver, String subject, EmailTemplate template) {
        String body = templateEngine.process(template.getViewPath(), new Context(KOREAN, template.getModel()));

        mailSender.send(receiver, subject, body);
    }

    public void send(String receiver, String subject, String body) {
        mailSender.send(receiver, subject, body);
    }
}
