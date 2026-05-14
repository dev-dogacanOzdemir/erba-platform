package com.appsolute.erba.notification.application.service;

import com.appsolute.erba.notification.application.dto.UnreadNotificationCountResult;
import com.appsolute.erba.notification.domain.port.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class GetUnreadNotificationCountService {

    private final NotificationRepository notificationRepository;

    public GetUnreadNotificationCountService(
            NotificationRepository notificationRepository
    ) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public UnreadNotificationCountResult get(UUID recipientUserId) {
        long count = notificationRepository
                .countUnreadByRecipientUserId(recipientUserId);

        return new UnreadNotificationCountResult(count);
    }
}