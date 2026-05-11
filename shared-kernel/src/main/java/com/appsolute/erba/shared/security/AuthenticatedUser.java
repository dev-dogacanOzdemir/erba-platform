package com.appsolute.erba.shared.security;

import java.util.List;
import java.util.UUID;

public record AuthenticatedUser(
        UUID userId,
        String email,
        String role,
        List<String> permissions
) {
}