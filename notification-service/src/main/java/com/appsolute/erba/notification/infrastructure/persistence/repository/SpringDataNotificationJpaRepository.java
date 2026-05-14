package com.appsolute.erba.notification.infrastructure.persistence.repository;

import com.appsolute.erba.notification.infrastructure.persistence.entity.NotificationJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface SpringDataNotificationJpaRepository
        extends JpaRepository<NotificationJpaEntity, UUID> {

    Page<NotificationJpaEntity> findByRecipientUserIdOrderByCreatedAtDesc(
            UUID recipientUserId,
            Pageable pageable
    );

    long countByRecipientUserIdAndReadFalse(UUID recipientUserId);

    @Modifying
    @Query("""
            update NotificationJpaEntity notification
            set notification.read = true,
                notification.readAt = :readAt
            where notification.recipientUserId = :recipientUserId
              and notification.read = false
            """)
    int markAllAsReadByRecipientUserId(
            @Param("recipientUserId") UUID recipientUserId,
            @Param("readAt") LocalDateTime readAt
    );
}