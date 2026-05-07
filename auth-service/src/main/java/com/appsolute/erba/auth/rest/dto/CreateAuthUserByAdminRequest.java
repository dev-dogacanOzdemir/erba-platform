package com.appsolute.erba.auth.rest.dto;

import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateAuthUserByAdminRequest(
        @NotBlank
        @Email
        String email,

        @NotBlank
        @Size(min = 8, max = 100)
        String temporaryPassword,

        @NotNull
        AuthRole role,

        @NotNull
        AuthUserStatus status
) {
}