package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.auth.application.dto.*;
import com.appsolute.erba.auth.application.service.LoginService;
import com.appsolute.erba.auth.application.service.RefreshService;
import com.appsolute.erba.auth.application.service.RegisterService;
import com.appsolute.erba.auth.infrastructure.security.cookie.RefreshTokenCookieService;
import com.appsolute.erba.auth.rest.dto.LoginRequest;
import com.appsolute.erba.auth.rest.dto.LoginResponse;
import com.appsolute.erba.auth.rest.dto.RegisterRequest;
import com.appsolute.erba.auth.rest.dto.RegisterResponse;
import com.appsolute.erba.shared.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final RegisterService registerService;
    private final LoginService loginService;
    private final RefreshService refreshService;
    private final RefreshTokenCookieService refreshTokenCookieService;

    public AuthController(
            RegisterService registerService,
            LoginService loginService,
            RefreshService refreshService,
            RefreshTokenCookieService refreshTokenCookieService
    ) {
        this.registerService = registerService;
        this.loginService = loginService;
        this.refreshService = refreshService;
        this.refreshTokenCookieService = refreshTokenCookieService;
    }

    @PostMapping("/register")
    public ApiResponse<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterResult result = registerService.register(toCommand(request));
        return ApiResponse.success(toResponse(result));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = loginService.login(toCommand(request));

        ResponseCookie refreshCookie =
                refreshTokenCookieService.createRefreshTokenCookie(result.refreshToken());

        LoginResponse response = new LoginResponse(
                result.accessToken(),
                "Bearer",
                result.expiresIn()
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(ApiResponse.success(response));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refresh(HttpServletRequest request) {

        String refreshToken =
                refreshTokenCookieService.extractRefreshToken(request);

        var result = refreshService.refresh(new RefreshCommand(refreshToken));

        ResponseCookie cookie =
                refreshTokenCookieService.createRefreshTokenCookie(result.refreshToken());

        LoginResponse response = new LoginResponse(
                result.accessToken(),
                "Bearer",
                result.expiresIn()
        );

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(ApiResponse.success(response));
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

    private LoginCommand toCommand(LoginRequest request) {
        return new LoginCommand(
                request.email(),
                request.password()
        );
    }
}