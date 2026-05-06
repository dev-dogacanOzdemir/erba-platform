package com.appsolute.erba.auth.application.dto;

public record RefreshResult(
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}