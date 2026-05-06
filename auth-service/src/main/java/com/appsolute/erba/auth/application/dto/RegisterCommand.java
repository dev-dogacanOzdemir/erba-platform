package com.appsolute.erba.auth.application.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;

public record RegisterCommand(
        String email,
        String password
) {
}