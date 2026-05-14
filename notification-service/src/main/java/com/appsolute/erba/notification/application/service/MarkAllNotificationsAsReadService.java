package com.appsolute.erba.notification.application.service;

import com.appsolute.erba.notification.domain.port.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MarkAllNotificationsAsReadService {

    private final NotificationRepository notificationRepository;

    public MarkAllNotificationsAsReadService(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void markAll(UUID recipientUserId) {
        notificationRepository
                .markAllAsReadByRecipientUserId(recipientUserId);
    }
}