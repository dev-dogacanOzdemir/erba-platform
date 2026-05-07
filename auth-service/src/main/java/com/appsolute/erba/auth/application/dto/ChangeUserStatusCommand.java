package com.appsolute.erba.auth.application.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;

import java.util.UUID;

public record ChangeUserStatusCommand(
        UUID userId,
        AuthUserStatus status
) {
}