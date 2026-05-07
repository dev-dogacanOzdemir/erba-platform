package com.appsolute.erba.identity.application.dto;

import java.util.UUID;

public record LinkAuthUserCommand(
        UUID identityUserId,
        UUID authUserId
) {
}