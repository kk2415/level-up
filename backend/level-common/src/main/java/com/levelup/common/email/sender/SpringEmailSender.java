package com.levelup.common.email.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import javax.mail.internet.MimeMessage;

@Slf4j
@RequiredArgsConstructor
@Component
public class SpringEmailSender implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(String receiver, String subject, String body) {
        mailSender.send((MimeMessage message) -> {
            final MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setTo(receiver);
            helper.setSubject(subject);
            helper.setText(body, true);
        });
    }

    @Async
    @Override
    public void asyncSend(String receiver, String subject, String body) {
        send(receiver, subject, body);
    }
}
