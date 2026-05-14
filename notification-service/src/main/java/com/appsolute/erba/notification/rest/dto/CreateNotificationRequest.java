package com.appsolute.erba.notification.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateNotificationRequest(
        @NotNull
        UUID recipientUserId,

        @NotBlank
        @Size(max = 200)
        String title,

        @NotBlank
        @Size(max = 1000)
        String message,

        @NotBlank
        @Size(max = 100)
        String type,

        @Size(max = 100)
        String sourceService,

        @Size(max = 100)
        String sourceResourceType,

        UUID sourceResourceId
) {
}