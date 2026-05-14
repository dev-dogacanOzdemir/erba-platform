package com.appsolute.erba.notification.application.service;

import com.appsolute.erba.notification.application.dto.CreateNotificationCommand;
import com.appsolute.erba.notification.application.dto.CreateNotificationResult;
import com.appsolute.erba.notification.domain.model.Notification;
import com.appsolute.erba.notification.domain.port.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateNotificationService {

    private final NotificationRepository notificationRepository;

    public CreateNotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public CreateNotificationResult create(CreateNotificationCommand command) {
        Notification notification = Notification.create(
                command.recipientUserId(),
                command.title(),
                command.message(),
                command.type(),
                command.sourceService(),
                command.sourceResourceType(),
                command.sourceResourceId()
        );

        Notification savedNotification =
                notificationRepository.save(notification);

        return new CreateNotificationResult(savedNotification.getId());
    }
}