package com.appsolute.erba.auth.domain.port;

import com.appsolute.erba.auth.domain.valueobject.UserId;

import java.time.LocalDateTime;

public interface RefreshTokenRepository {

    void revokeActiveTokensByUserId(UserId userId);

    void save(
            UserId userId,
            String tokenHash,
            LocalDateTime expiresAt
    );
}