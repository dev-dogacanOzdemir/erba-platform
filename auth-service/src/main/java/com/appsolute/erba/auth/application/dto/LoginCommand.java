package com.appsolute.erba.auth.application.dto;

public record LoginCommand(
        String email,
        String password
) {
}