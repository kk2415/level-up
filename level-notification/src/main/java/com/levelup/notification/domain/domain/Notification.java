package com.levelup.notification.domain.domain;

import com.levelup.notification.domain.constant.NotificationTemplateType;
import com.levelup.notification.domain.constant.NotificationType;
import com.levelup.notification.domain.entity.NotificationEntity;
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

    public static Notification from(NotificationEntity entity) {
        return new Notification(
                entity.getId(),
                entity.getTitle(),
                entity.getReceiverId(),
                entity.getActivatorId(),
                entity.getReadAt(),
                entity.getIsRead(),
                entity.getNotificationType(),
                entity.getTemplateType(),
                NotificationTemplate.from(entity.getTemplate()));
    }

    public NotificationEntity toEntity() {
        NotificationEntity entity = NotificationEntity.of(
                this.id,
                this.title,
                this.receiverId,
                this.activatorId,
                this.readAt,
                this.isRead,
                this.notificationType,
                this.templateType,
                this.template.toEntity()
        );

        return entity;
    }
}
