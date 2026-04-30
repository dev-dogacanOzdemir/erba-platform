package com.appsolute.erba.shared.response;

public record ErrorResponse(
        String code,
        String message
) {
}