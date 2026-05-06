package com.appsolute.erba.auth.application.dto;

public record LogoutCommand(
        String refreshToken
) {
}