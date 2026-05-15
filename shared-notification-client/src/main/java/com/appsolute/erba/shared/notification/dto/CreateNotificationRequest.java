package com.appsolute.erba.shared.notification.dto;

import java.util.UUID;

public record CreateNotificationRequest(
        UUID recipientUserId,
        String title,
        String message,
        String type,
        String sourceService,
        String sourceResourceType,
        UUID sourceResourceId
) {
}