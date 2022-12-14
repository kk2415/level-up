package com.levelup.notification.domain.domain;

import com.levelup.notification.domain.constant.NotificationTemplateType;
import com.levelup.notification.domain.entity.NotificationTemplateEntity;

public class JobNotificationTemplate extends NotificationTemplate {

    private JobNotificationTemplate(Long id, String title, String body) {
        super(id, title, body);
    }

    public static JobNotificationTemplate of(Long id, String title, String body) {
        return new JobNotificationTemplate(id, title, body);
    }

    public static NotificationTemplateEntity from(String body) {
        return NotificationTemplateEntity.of(null, createTitle(), createBody(body));
    }

    public NotificationTemplateEntity toEntity() {
        return NotificationTemplateEntity.of(this.getId(), this.getTitle(), this.getBody());
    }

    private static String createTitle() {
        return "새로운 공고가 올라왔습니다!";
    }

    private static String createBody(String body) {
        return body;
    }

    public boolean isSupports(NotificationTemplateType templateType) {
        return NotificationTemplateType.NEW_JOB.equals(templateType);
    }
}
