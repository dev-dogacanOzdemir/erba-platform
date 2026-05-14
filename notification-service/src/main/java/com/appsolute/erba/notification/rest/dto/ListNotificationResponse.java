package com.appsolute.erba.notification.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListNotificationResponse(
        UUID id,
        UUID recipientUserId,
        String title,
        String message,
        String type,
        String sourceService,
        String sourceResourceType,
        UUID sourceResourceId,
        boolean read,
        LocalDateTime createdAt,
        LocalDateTime readAt
) {
}