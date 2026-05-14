package com.appsolute.erba.notification.application.service;

import com.appsolute.erba.notification.application.dto.ListNotificationResult;
import com.appsolute.erba.notification.domain.model.Notification;
import com.appsolute.erba.notification.domain.port.NotificationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ListMyNotificationsService {

    private final NotificationRepository notificationRepository;

    public ListMyNotificationsService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public Page<ListNotificationResult> list(
            UUID recipientUserId,
            int page,
            int size
    ) {
        return notificationRepository.findByRecipientUserId(
                        recipientUserId,
                        PageRequest.of(page, size)
                )
                .map(this::toResult);
    }

    private ListNotificationResult toResult(Notification notification) {
        return new ListNotificationResult(
                notification.getId(),
                notification.getRecipientUserId(),
                notification.getTitle(),
                notification.getMessage(),
                notification.getType(),
                notification.getSourceService(),
                notification.getSourceResourceType(),
                notification.getSourceResourceId(),
                notification.isRead(),
                notification.getCreatedAt(),
                notification.getReadAt()
        );
    }
}