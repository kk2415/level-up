package com.levelup.api.controller.v1.dto;

import com.levelup.api.enumeration.NotificationTemplateType;
import com.levelup.api.enumeration.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ChannelJoinRequestNotificationDto {

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @Getter
    public static class Request {

        private Long receiverId;
        private Long activatorId; /* member who occur notification */
        private Long activatorName; /* member who occur notification */
        private Long channelId;
        private Long channelName;
        private NotificationType notificationType;
        private NotificationTemplateType templateType;
    }
}
