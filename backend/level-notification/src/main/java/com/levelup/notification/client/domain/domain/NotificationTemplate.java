package com.levelup.notification.client.domain.domain;

import com.levelup.notification.client.domain.constant.NotificationTemplateType;
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

    public abstract boolean isSupports(NotificationTemplateType templateType);
}
