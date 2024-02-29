package com.levelup.notification.domain.repository;

import com.levelup.notification.domain.entity.NotificationTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationTemplateRepository extends JpaRepository<NotificationTemplateEntity, Long> {
}
