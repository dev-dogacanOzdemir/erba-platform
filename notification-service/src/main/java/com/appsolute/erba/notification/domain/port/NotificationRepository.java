package com.appsolute.erba.notification.domain.port;

import com.appsolute.erba.notification.domain.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {

    Notification save(Notification notification);

    Optional<Notification> findById(UUID id);

    Page<Notification> findByRecipientUserId(UUID recipientUserId, Pageable pageable);

    long countUnreadByRecipientUserId(UUID recipientUserId);

    void markAllAsReadByRecipientUserId(UUID recipientUserId);
}