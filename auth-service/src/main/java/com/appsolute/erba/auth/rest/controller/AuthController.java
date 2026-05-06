package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.auth.application.dto.*;
import com.appsolute.erba.auth.application.service.*;
import com.appsolute.erba.auth.infrastructure.security.cookie.RefreshTokenCookieService;
import com.appsolute.erba.auth.rest.dto.*;
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
    private final LogoutService logoutService;
    private final ForgotPasswordService forgotPasswordService;
    private final ResetPasswordService resetPasswordService;
    private final RefreshTokenCookieService refreshTokenCookieService;

    public AuthController(
            RegisterService registerService,
            LoginService loginService,
            RefreshService refreshService,
            LogoutService logoutService,
            ForgotPasswordService forgotPasswordService,
            ResetPasswordService resetPasswordService,
            RefreshTokenCookieService refreshTokenCookieService
    ) {
        this.registerService = registerService;
        this.loginService = loginService;
        this.refreshService = refreshService;
        this.logoutService = logoutService;
        this.forgotPasswordService = forgotPasswordService;
        this.resetPasswordService = resetPasswordService;
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

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request) {
        try {
            String refreshToken = refreshTokenCookieService.extractRefreshToken(request);
            logoutService.logout(new LogoutCommand(refreshToken));
        } catch (IllegalArgumentException ignored) {
            // Cookie yoksa da logout başarılı kabul edilir.
        }

        ResponseCookie clearCookie = refreshTokenCookieService.clearRefreshTokenCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, clearCookie.toString())
                .body(ApiResponse.success(null));
    }

    @PostMapping("/forgot-password")
    public ApiResponse<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        forgotPasswordService.forgotPassword(
                new ForgotPasswordCommand(request.email())
        );

        return ApiResponse.success(null);
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordService.resetPassword(
                new ResetPasswordCommand(
                        request.resetToken(),
                        request.newPassword()
                )
        );

        return ApiResponse.success(null);
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