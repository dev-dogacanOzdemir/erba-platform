package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.auth.application.dto.RegisterCommand;
import com.appsolute.erba.auth.application.dto.RegisterResult;
import com.appsolute.erba.auth.application.service.RegisterService;
import com.appsolute.erba.auth.rest.dto.RegisterRequest;
import com.appsolute.erba.auth.rest.dto.RegisterResponse;
import com.appsolute.erba.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterService registerService;

    public AuthController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResult result = registerService.register(toCommand(request));
        return ApiResponse.success(toResponse(result));
    }

    private RegisterCommand toCommand(RegisterRequest request) {
        return new RegisterCommand(
                request.email(),
                request.password()
        );
    }

    private RegisterResponse toResponse(RegisterResult result) {
        return new RegisterResponse(
                result.userId(),
                result.email(),
                result.role().name(),
                result.role().getLabel(),
                result.status().name(),
                result.status().getLabel()
        );
    }
}