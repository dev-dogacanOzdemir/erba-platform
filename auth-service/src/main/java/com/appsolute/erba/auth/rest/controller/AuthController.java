package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.auth.application.dto.LoginRequest;
import com.appsolute.erba.auth.application.dto.LoginResponse;
import com.appsolute.erba.auth.application.usecase.LoginUseCase;
import com.appsolute.erba.shared.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = loginUseCase.login(request);
        return ApiResponse.success(response);
    }
}