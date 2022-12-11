package com.levelup.notification.client.domain.service;

import com.levelup.notification.client.domain.constant.NotificationTemplateType;
import com.levelup.notification.client.domain.domain.Notification;

public interface NotificationService {

    boolean isSupport(NotificationTemplateType templateType);
    Notification create(Notification notification);
}
