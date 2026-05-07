package com.appsolute.erba.auth.application.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;

public record CreateAuthUserByAdminCommand(
        String email,
        String temporaryPassword,
        AuthRole role,
        AuthUserStatus status
) {
}