package com.appsolute.erba.auth.application.dto;

import java.util.UUID;

public record RefreshTokenResult(
        UUID userId,
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}