package com.appsolute.erba.auth.application.dto;

import java.util.UUID;

public record LoginResult(
        UUID userId,
        String accessToken,
        String refreshToken,
        long expiresIn
) {
}