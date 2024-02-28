package com.levelup.api.controller.v1.dto;

import com.levelup.api.enumeration.NotificationTemplateType;
import com.levelup.api.enumeration.NotificationType;
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
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Response {
        private Long receiverId;
        private Long activatorId;
        private NotificationType notificationType;
        private NotificationTemplateType templateType;
    }
}
