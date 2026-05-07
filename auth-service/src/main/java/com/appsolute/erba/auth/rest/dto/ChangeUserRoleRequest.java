package com.appsolute.erba.auth.rest.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import jakarta.validation.constraints.NotNull;

public record ChangeUserRoleRequest(
        @NotNull
        AuthRole role
) {
}