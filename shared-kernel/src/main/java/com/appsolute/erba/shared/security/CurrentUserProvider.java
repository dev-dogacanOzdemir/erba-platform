package com.appsolute.erba.shared.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

public class CurrentUserProvider {

    public Optional<AuthenticatedUser> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof AuthenticatedUser user)) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    public UUID getCurrentUserIdOrThrow() {
        return getCurrentUser()
                .map(AuthenticatedUser::userId)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }

    public String getCurrentUserRoleOrThrow() {
        return getCurrentUser()
                .map(AuthenticatedUser::role)
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }
}