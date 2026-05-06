package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.LoginCommand;
import com.appsolute.erba.auth.application.dto.LoginResult;
import com.appsolute.erba.auth.application.port.PasswordHasher;
import com.appsolute.erba.auth.application.port.TokenGenerator;
import com.appsolute.erba.auth.domain.exception.AuthUserLockedException;
import com.appsolute.erba.auth.domain.exception.InvalidCredentialsException;
import com.appsolute.erba.auth.domain.model.AuthUser;
import com.appsolute.erba.auth.domain.model.RefreshToken;
import com.appsolute.erba.auth.domain.port.AuthUserRepository;
import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import com.appsolute.erba.auth.domain.valueobject.AuthUserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class LoginService {

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final long LOCK_MINUTES = 15;
    private static final long ACCESS_TOKEN_EXPIRES_IN_SECONDS = 900;
    private static final long REFRESH_TOKEN_EXPIRES_IN_DAYS = 7;

    private final AuthUserRepository authUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordHasher passwordHasher;
    private final TokenGenerator tokenGenerator;

    public LoginService(
            AuthUserRepository authUserRepository,
            RefreshTokenRepository refreshTokenRepository,
            PasswordHasher passwordHasher,
            TokenGenerator tokenGenerator
    ) {
        this.authUserRepository = authUserRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordHasher = passwordHasher;
        this.tokenGenerator = tokenGenerator;
    }

    @Transactional
    public LoginResult login(LoginCommand command) {
        AuthUser authUser = authUserRepository.findByEmail(command.email())
                .filter(user -> !user.isDeleted())
                .orElseThrow(InvalidCredentialsException::new);

        if (authUser.isLocked()) {
            throw new AuthUserLockedException();
        }

        if (authUser.getStatus() == AuthUserStatus.PASSIVE) {
            throw new InvalidCredentialsException();
        }

        if (!passwordHasher.matches(command.password(), authUser.getPasswordHash())) {
            authUser.recordFailedLogin(MAX_FAILED_ATTEMPTS, LOCK_MINUTES);
            authUserRepository.save(authUser);

            if (authUser.isLocked()) {
                throw new AuthUserLockedException();
            }

            throw new InvalidCredentialsException();
        }

        authUser.recordSuccessfulLogin();
        authUserRepository.save(authUser);

        refreshTokenRepository.revokeAllActiveTokensByUserId(authUser.getId());

        String accessToken = tokenGenerator.generateAccessToken(
                authUser.getId(),
                authUser.getEmail(),
                authUser.getRole()
        );

        String rawRefreshToken = tokenGenerator.generateRefreshToken();
        String refreshTokenHash = tokenGenerator.hashToken(rawRefreshToken);

        RefreshToken refreshToken = RefreshToken.create(
                authUser.getId(),
                refreshTokenHash,
                LocalDateTime.now().plusDays(REFRESH_TOKEN_EXPIRES_IN_DAYS)
        );

        refreshTokenRepository.save(refreshToken);

        return new LoginResult(
                authUser.getId(),
                accessToken,
                rawRefreshToken,
                ACCESS_TOKEN_EXPIRES_IN_SECONDS
        );
    }
}