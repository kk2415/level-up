package com.levelup.notification.client.domain.domain;

import com.levelup.notification.client.domain.constant.NotificationTemplateType;
import com.levelup.notification.client.domain.constant.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Notification {

    private Long id;
    private String title;
    private Long receiverId;
    private Long activatorId; /* member who occur notification */
    private LocalDate readAt;
    private Boolean isRead;
    private NotificationType notificationType;
    private NotificationTemplateType templateType;
    private NotificationTemplate template;

    public static Notification of(
            Long receiverId,
            Long activatorId,
            NotificationType notificationType,
            NotificationTemplateType templateType,
            NotificationTemplate template)
    {
        return new Notification(
                null,
                "title",
                receiverId,
                activatorId,
                null,
                false,
                notificationType,
                templateType,
                template);
    }
}
