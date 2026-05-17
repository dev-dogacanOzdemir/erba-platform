package com.appsolute.erba.notification.infrastructure.client.dto;

import java.util.UUID;

public record InternalAuthUserResponse(
        UUID userId,
        String email
) {
}