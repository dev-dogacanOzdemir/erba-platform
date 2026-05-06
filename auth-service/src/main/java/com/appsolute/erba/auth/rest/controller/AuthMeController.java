package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.shared.response.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AuthMeController {

    @GetMapping("/api/v1/auth/me")
    public ApiResponse<MeResponse> me(Authentication authentication) {
        UUID userId = (UUID) authentication.getPrincipal();

        return ApiResponse.success(
                new MeResponse(
                        userId,
                        authentication.getAuthorities().stream()
                                .map(Object::toString)
                                .toList()
                )
        );
    }

    public record MeResponse(
            UUID userId,
            Object roles
    ) {
    }
}