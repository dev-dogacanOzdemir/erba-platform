package com.appsolute.erba.auth.domain.port;

import com.appsolute.erba.auth.domain.model.PasswordResetToken;

import java.util.Optional;
import java.util.UUID;

public interface PasswordResetTokenRepository {

    PasswordResetToken save(PasswordResetToken token);

    Optional<PasswordResetToken> findByTokenHash(String tokenHash);

    void revokeAllUsableTokensByUserId(UUID userId);
}