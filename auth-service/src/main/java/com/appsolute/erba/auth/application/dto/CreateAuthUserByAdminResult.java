package com.appsolute.erba.auth.application.dto;

import java.util.UUID;

public record CreateAuthUserByAdminResult(
        UUID userId,
        String email,
        String role,
        String roleLabel,
        String status,
        String statusLabel
) {
}