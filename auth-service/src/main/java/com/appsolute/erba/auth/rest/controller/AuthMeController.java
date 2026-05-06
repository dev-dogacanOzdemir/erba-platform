package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.shared.response.ApiResponse;
import com.appsolute.erba.shared.security.AuthenticatedUser;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthMeController {

    @GetMapping("/api/v1/auth/me")
    public ApiResponse<MeResponse> me(Authentication authentication) {
        AuthenticatedUser user = (AuthenticatedUser) authentication.getPrincipal();

        return ApiResponse.success(
                new MeResponse(
                        user.userId(),
                        user.email(),
                        user.role()
                )
        );
    }

    public record MeResponse(
            Object userId,
            String email,
            String role
    ) {
    }
}