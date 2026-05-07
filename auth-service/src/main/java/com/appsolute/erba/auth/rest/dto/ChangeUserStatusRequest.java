package com.appsolute.erba.auth.rest.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;
import jakarta.validation.constraints.NotNull;

public record ChangeUserStatusRequest(
        @NotNull
        AuthUserStatus status
) {
}