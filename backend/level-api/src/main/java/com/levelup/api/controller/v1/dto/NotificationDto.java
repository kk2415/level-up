package com.levelup.api.controller.v1.dto;

import com.levelup.notification.domain.constant.NotificationTemplateType;
import com.levelup.notification.domain.constant.NotificationType;
import com.levelup.notification.domain.domain.NewApplicantNotificationTemplate;
import com.levelup.notification.domain.domain.Notification;
import com.levelup.notification.domain.domain.NotificationTemplate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NotificationDto {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class NewApplicantRequest {

        private Long receiverId;
        private Long activatorId; /* member who occur notification */
        private String activatorName; /* member who occur notification */
        private Long channelId;
        private String channelName;
        private NotificationType notificationType;
        private NotificationTemplateType templateType;

        public Notification toDomain() {
            return Notification.of(
                    this.receiverId,
                    this.activatorId,
                    this.notificationType,
                    this.templateType,
                    NewApplicantNotificationTemplate.of(activatorName, channelName)
            );
        }
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Response {
        private Long receiverId;
        private Long activatorId;
        private NotificationType notificationType;
        private NotificationTemplateType templateType;
        private NotificationTemplate template;

        public static Response from(Notification notification) {
            return new Response(
                    notification.getReceiverId(),
                    notification.getActivatorId(),
                    notification.getNotificationType(),
                    notification.getTemplateType(),
                    notification.getTemplate());
        }
    }
}
