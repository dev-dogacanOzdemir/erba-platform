package com.appsolute.erba.notification.application.service;

import com.appsolute.erba.notification.domain.model.Notification;
import com.appsolute.erba.notification.domain.port.NotificationRepository;
import com.appsolute.erba.shared.exception.ErrorCode;
import com.appsolute.erba.shared.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class MarkNotificationAsReadService {

    private final NotificationRepository notificationRepository;

    public MarkNotificationAsReadService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void markAsRead(UUID authenticatedUserId, UUID notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .filter(item -> item.getRecipientUserId().equals(authenticatedUserId))
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOTIFICATION_NOT_FOUND));

        notification.markAsRead();

        notificationRepository.save(notification);
    }
}