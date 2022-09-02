package com.levelup.api.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender sender;

    public void send(String toEmail, String subject, String body) {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(body, true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        sender.send(message);
    }

    @Async
    public void sendConfirmEmail(String toEmail, String securityCode) {
        String subject = "회원가입 이메일 인증";
        String body = "";

        body += "<div style='margin:100px;'>";
        body += "<h1> Level Up. </h1>";
        body += "<br>";
        body += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        body += "<br>";
        body += "<p>감사합니다!<p>";
        body += "<br>";
        body += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        body += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        body += "<div style='font-size:130%'>";
        body += "CODE : <strong>";
        body += securityCode + "</strong><div><br/> ";
        body += "</div>";


        send(toEmail, subject, body);
    }
}
