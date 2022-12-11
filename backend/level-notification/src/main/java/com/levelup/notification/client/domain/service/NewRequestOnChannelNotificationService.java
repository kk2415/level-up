package com.levelup.notification.client.domain.service;

import com.levelup.notification.client.domain.constant.NotificationTemplateType;
import com.levelup.notification.client.domain.domain.Notification;

public class NewRequestOnChannelNotificationService implements NotificationService {

    @Override
    public boolean isSupport(NotificationTemplateType templateType) {
        return NotificationTemplateType.NEW_REQUEST_ON_CHANNEL.equals(templateType);
    }

    @Override
    public Notification create(Notification notification) {
        return null;
    }
}
