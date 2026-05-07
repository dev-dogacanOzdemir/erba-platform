package com.appsolute.erba.auth.rest.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListAuthUserResponse(
        UUID id,
        String email,
        String role,
        String roleLabel,
        String status,
        String statusLabel,
        int failedLoginCount,
        LocalDateTime lockedUntil,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}