package com.levelup.notification.domain.domain;

import com.levelup.notification.domain.constant.NotificationTemplateType;
import com.levelup.notification.domain.entity.NotificationTemplateEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class NotificationTemplate {

    private Long id;
    private String title;
    private String body;

    public static NotificationTemplate from(NotificationTemplateEntity template) {
        return new NotificationTemplate(template.getId(), template.getTitle(), template.getBody()) {
            @Override
            public boolean isSupports(NotificationTemplateType templateType) {
                return false;
            }

            @Override
            public NotificationTemplateEntity toEntity() {
                return template;
            }
        };
    }

    public abstract boolean isSupports(NotificationTemplateType templateType);

    public abstract NotificationTemplateEntity toEntity();
}