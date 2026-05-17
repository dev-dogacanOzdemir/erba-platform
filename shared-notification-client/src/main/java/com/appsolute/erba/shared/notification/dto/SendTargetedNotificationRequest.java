package com.appsolute.erba.shared.notification.dto;

import java.util.UUID;

public record SendTargetedNotificationRequest(
        String targetType,
        String targetKey,
        String title,
        String message,
        String type,
        String sourceService,
        String sourceResourceType,
        UUID sourceResourceId
) {
}