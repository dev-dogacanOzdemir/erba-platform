package com.appsolute.erba.notification.rest.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record InternalCreateTargetedNotificationRequest(

        @NotBlank
        String targetType,

        @NotBlank
        String targetKey,

        @NotBlank
        String title,

        @NotBlank
        String message,

        @NotBlank
        String type,

        @NotBlank
        String sourceService,

        @NotBlank
        String sourceResourceType,

        UUID sourceResourceId
) {
}