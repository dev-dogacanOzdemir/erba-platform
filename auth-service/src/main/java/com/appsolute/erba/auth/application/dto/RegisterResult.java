package com.appsolute.erba.auth.application.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;

import java.util.UUID;

public record RegisterResult(
        UUID userId,
        String email,
        AuthRole role,
        AuthUserStatus status
) {
}