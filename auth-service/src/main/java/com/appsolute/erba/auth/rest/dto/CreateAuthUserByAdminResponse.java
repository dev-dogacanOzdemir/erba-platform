package com.appsolute.erba.auth.rest.dto;

import java.util.UUID;

public record CreateAuthUserByAdminResponse(
        UUID userId,
        String email,
        String role,
        String roleLabel,
        String status,
        String statusLabel
) {
}