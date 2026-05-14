package com.appsolute.erba.notification.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListNotificationResult(
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