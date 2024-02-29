package com.levelup.notification.domain.entity;

import com.levelup.notification.domain.constant.NotificationTemplateType;
import com.levelup.notification.domain.constant.NotificationType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "notification")
@Entity
public class NotificationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String title;
    private Long receiverId;
    private Long activatorId; /* member who occur notification */
    private LocalDate readAt;
    private Boolean isRead;
    private NotificationType notificationType;
    private NotificationTemplateType templateType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "notification_template_id")
    private NotificationTemplateEntity template;

    public static NotificationEntity of(
            Long id,
            String title,
            Long receiverId,
            Long activatorId,
            LocalDate readAt,
            Boolean isRead,
            NotificationType notificationType,
            NotificationTemplateType templateType,
            NotificationTemplateEntity template)
    {
        return new NotificationEntity(
                id,
                title,
                receiverId,
                activatorId,
                readAt,
                isRead,
                notificationType,
                templateType,
                template);
    }
}
