package com.appsolute.erba.auth.application.port;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;

import java.util.UUID;

public interface TokenValidator {

    ValidatedToken validateAccessToken(String token);

    record ValidatedToken(
            UUID userId,
            String email,
            AuthRole role
    ) {
    }
}