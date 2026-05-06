package com.appsolute.erba.auth.rest.dto;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}