package com.appsolute.erba.auth.application.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;

import java.util.UUID;

public record CreateAuthUserByAdminCommand(
        UUID actorUserId,
        String email,
        String temporaryPassword,
        AuthRole role,
        AuthUserStatus status
) {
}
