package com.appsolute.erba.auth.application.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;

import java.util.UUID;

public record ChangeUserRoleCommand(
        UUID userId,
        AuthRole role
) {
}