package com.levelup.notification.domain.domain;

import com.levelup.notification.domain.constant.NotificationTemplateType;
import com.levelup.notification.domain.entity.NotificationTemplateEntity;

public class NewApplicantNotificationTemplate extends NotificationTemplate {

    private NewApplicantNotificationTemplate(Long id, String title, String body) {
        super(id, title, body);
    }

    public static NewApplicantNotificationTemplate of(Long id, String title, String body) {
        return new NewApplicantNotificationTemplate(id, title, body);
    }

    public static NotificationTemplate of(String applicantName, String channelName) {
        return NewApplicantNotificationTemplate.of(null, createTitle(), createBody(applicantName, channelName));
    }

    public NotificationTemplateEntity toEntity() {
        return NotificationTemplateEntity.of(this.getId(), this.getTitle(), this.getBody());
    }

    private static String createTitle() {
        return "누군가 나의 스터디/프로젝트에 지원 했어요!";
    }

    private static String createBody(String applicantName, String channelName) {
        return applicantName + "님이 " + channelName + "에 가입을 신청하였습니다.";
    }

    public boolean isSupports(NotificationTemplateType templateType) {
        return NotificationTemplateType.NEW_JOB.equals(templateType);
    }
}
