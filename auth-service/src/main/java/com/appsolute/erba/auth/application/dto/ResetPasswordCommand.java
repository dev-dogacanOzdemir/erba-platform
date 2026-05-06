package com.appsolute.erba.auth.application.dto;

public record ResetPasswordCommand(
        String resetToken,
        String newPassword
) {
}