package com.levelup.notification.client.domain.repository;

import com.levelup.notification.client.domain.entity.NotificationTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplateEntity, Long> {
}
