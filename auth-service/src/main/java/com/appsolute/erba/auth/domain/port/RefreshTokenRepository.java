package com.appsolute.erba.auth.domain.port;

import com.appsolute.erba.auth.domain.model.RefreshToken;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {

    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByTokenHash(String tokenHash);

    List<RefreshToken> findActiveTokensByUserId(UUID userId);

    void revokeAllActiveTokensByUserId(UUID userId);
}