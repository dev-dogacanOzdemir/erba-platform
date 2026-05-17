package com.appsolute.erba.auth.rest.dto;

import java.util.UUID;

public record InternalAuthUserResponse(
        UUID userId,
        String email
) {
}