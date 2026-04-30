package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.LoginRequest;
import com.appsolute.erba.auth.application.dto.LoginResponse;
import com.appsolute.erba.auth.application.usecase.LoginUseCase;
import com.appsolute.erba.auth.domain.model.User;
import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import com.appsolute.erba.auth.domain.port.TokenHasher;
import com.appsolute.erba.auth.domain.port.TokenProvider;
import com.appsolute.erba.auth.domain.port.UserRepository;
import com.appsolute.erba.auth.domain.service.AuthenticationService;
import com.appsolute.erba.auth.domain.valueobject.Email;
import com.appsolute.erba.shared.exception.BaseException;
import com.appsolute.erba.shared.exception.ErrorCode;

import java.time.LocalDateTime;

public class AuthApplicationService implements LoginUseCase {

    private static final long ACCESS_TOKEN_EXPIRES_IN = 3600L;

    private final UserRepository userRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationService authenticationService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenHasher tokenHasher;

    public AuthApplicationService(
            UserRepository userRepository,
            TokenProvider tokenProvider,
            AuthenticationService authenticationService,
            RefreshTokenRepository refreshTokenRepository,
            TokenHasher tokenHasher
    ) {
        this.userRepository = userRepository;
        this.tokenProvider = tokenProvider;
        this.authenticationService = authenticationService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.tokenHasher = tokenHasher;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(Email.of(request.getEmail()))
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_CREDENTIALS));

        authenticationService.authenticate(user, request.getPassword());

        userRepository.save(user);

        String accessToken = tokenProvider.generateAccessToken(user);
        String refreshToken = tokenProvider.generateRefreshToken(user);

        String refreshTokenHash = tokenHasher.hash(refreshToken);

        refreshTokenRepository.revokeActiveTokensByUserId(user.getId());

        refreshTokenRepository.save(
                user.getId(),
                refreshTokenHash,
                LocalDateTime.now().plusDays(7)
        );

        return new LoginResponse(
                accessToken,
                refreshToken,
                ACCESS_TOKEN_EXPIRES_IN

        );
    }
}