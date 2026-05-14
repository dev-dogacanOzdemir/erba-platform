package com.appsolute.erba.notification.infrastructure.persistence.repository;

import com.appsolute.erba.notification.infrastructure.persistence.entity.NotificationJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataNotificationJpaRepository
        extends JpaRepository<NotificationJpaEntity, UUID> {

    List<NotificationJpaEntity> findByRecipientUserIdOrderByCreatedAtDesc(
            UUID recipientUserId
    );
}