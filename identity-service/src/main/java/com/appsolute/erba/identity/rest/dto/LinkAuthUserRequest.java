package com.appsolute.erba.identity.rest.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record LinkAuthUserRequest(

        @NotNull(message = "Auth user id boş olamaz")
        UUID authUserId

) {
}