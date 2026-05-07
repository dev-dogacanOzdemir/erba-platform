package com.appsolute.erba.auth.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListAuthUserResult(
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