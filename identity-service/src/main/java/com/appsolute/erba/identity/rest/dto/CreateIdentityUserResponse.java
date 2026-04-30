package com.appsolute.erba.identity.rest.dto;

import java.util.UUID;

public record CreateIdentityUserResponse(
        UUID identityUserId
) {
}