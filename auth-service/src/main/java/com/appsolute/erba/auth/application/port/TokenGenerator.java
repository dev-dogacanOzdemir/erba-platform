package com.appsolute.erba.auth.application.port;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;

import java.util.UUID;

public interface TokenGenerator {

    String generateAccessToken(UUID userId, String email, AuthRole role);

    String generateRefreshToken();

    String generatePasswordResetToken();

    String hashToken(String rawToken);
}