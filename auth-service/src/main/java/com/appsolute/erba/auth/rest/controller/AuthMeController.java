package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.shared.response.ApiResponse;
import com.appsolute.erba.shared.security.AuthenticatedUser;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
public class AuthMeController {

    @GetMapping("/api/v1/auth/me")
    public ApiResponse<MeResponse> me(
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    ) {
        return ApiResponse.success(
                new MeResponse(
                        authenticatedUser.userId(),
                        authenticatedUser.email(),
                        authenticatedUser.role(),
                        authenticatedUser.permissions()
                )
        );
    }

    public record MeResponse(
            UUID userId,
            String email,
            String role,
            List<String> permissions
    ) {
    }
}