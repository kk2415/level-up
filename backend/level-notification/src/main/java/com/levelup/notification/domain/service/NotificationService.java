package com.levelup.notification.domain.service;

import com.levelup.notification.domain.constant.NotificationType;
import com.levelup.notification.domain.domain.Notification;
import com.levelup.notification.domain.entity.NotificationEntity;
import com.levelup.notification.domain.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification create(Notification notification) {
        NotificationEntity notificationEntity = notification.toEntity();
        notificationRepository.save(notificationEntity);

        if (!NotificationType.NONE.equals(notification.getNotificationType())) {
            //TODO:: 알림 발송 TO ANDROID, IOS, SMS, EMAIL
        }

        return Notification.from(notificationEntity);
    }
}
