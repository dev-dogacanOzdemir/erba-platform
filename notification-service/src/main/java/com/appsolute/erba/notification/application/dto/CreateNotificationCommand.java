package com.appsolute.erba.notification.application.dto;

import java.util.UUID;

public record CreateNotificationCommand(
        UUID recipientUserId,
        String title,
        String message,
        String type,
        String sourceService,
        String sourceResourceType,
        UUID sourceResourceId
) {
}