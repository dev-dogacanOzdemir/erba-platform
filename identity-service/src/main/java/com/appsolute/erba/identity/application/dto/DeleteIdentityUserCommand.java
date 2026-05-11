package com.appsolute.erba.identity.application.dto;

import java.util.UUID;

public record DeleteIdentityUserCommand(
        UUID actorUserId,
        UUID identityUserId
) {
}