package com.levelup.common.util.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.internet.MimeMessage;

import static java.util.Locale.KOREAN;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender sender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(EmailStuff stuff) {
        MimeMessagePreparator messagePreparatory = getMimeMessagePreparatory(stuff);

        sender.send(messagePreparatory);
    }

    public MimeMessagePreparator getMimeMessagePreparatory(EmailStuff stuff) {
        return (MimeMessage message) -> {
            final MimeMessageHelper helper = new MimeMessageHelper(message);
            String template =
                    templateEngine.process(stuff.getTemplateName().getFileName(), new Context(KOREAN, stuff.getProps()));

            helper.setTo(stuff.getEmail());
            helper.setSubject(stuff.getSubject().getSubject());
            helper.setText(template, true);
        };
    }
}
