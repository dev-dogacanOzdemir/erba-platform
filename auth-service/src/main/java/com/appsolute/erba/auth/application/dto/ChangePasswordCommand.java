package com.appsolute.erba.auth.application.dto;

import java.util.UUID;

public record ChangePasswordCommand(
        UUID userId,
        String currentPassword,
        String newPassword
) {
}