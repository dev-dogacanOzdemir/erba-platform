package com.appsolute.erba.notification.application.service;

import com.appsolute.erba.notification.application.dto.CreateTargetedNotificationCommand;
import com.appsolute.erba.notification.application.dto.CreateTargetedNotificationResult;
import com.appsolute.erba.notification.application.port.AuthUserResolver;
import com.appsolute.erba.notification.domain.model.Notification;
import com.appsolute.erba.notification.domain.port.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreateTargetedNotificationService {

    private static final String TARGET_TYPE_PERMISSION = "PERMISSION";

    private final NotificationRepository notificationRepository;
    private final AuthUserResolver authUserResolver;

    public CreateTargetedNotificationService(
            NotificationRepository notificationRepository,
            AuthUserResolver authUserResolver
    ) {
        this.notificationRepository = notificationRepository;
        this.authUserResolver = authUserResolver;
    }

    @Transactional
    public CreateTargetedNotificationResult create(
            CreateTargetedNotificationCommand command
    ) {
        if (!TARGET_TYPE_PERMISSION.equalsIgnoreCase(command.targetType())) {
            throw new IllegalArgumentException(
                    "Unsupported notification target type: " + command.targetType()
            );
        }

        List<AuthUserResolver.ResolvedAuthUser> targetUsers =
                authUserResolver.findUsersByPermission(command.targetKey());

        int createdCount = 0;

        for (AuthUserResolver.ResolvedAuthUser user : targetUsers) {
            Notification notification = Notification.create(
                    user.userId(),
                    command.title(),
                    command.message(),
                    command.type(),
                    command.sourceService(),
                    command.sourceResourceType(),
                    command.sourceResourceId()
            );

            notificationRepository.save(notification);
            createdCount++;
        }

        return new CreateTargetedNotificationResult(createdCount);
    }
}