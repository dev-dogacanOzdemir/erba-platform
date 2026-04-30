package com.appsolute.erba.auth.application.service;

import com.appsolute.erba.auth.application.dto.LoginRequest;
import com.appsolute.erba.auth.application.dto.LoginResponse;
import com.appsolute.erba.auth.domain.model.User;
import com.appsolute.erba.auth.domain.port.RefreshTokenRepository;
import com.appsolute.erba.auth.domain.port.TokenHasher;
import com.appsolute.erba.auth.domain.port.TokenProvider;
import com.appsolute.erba.auth.domain.port.UserRepository;
import com.appsolute.erba.auth.domain.service.AuthenticationService;
import com.appsolute.erba.auth.domain.valueobject.Email;
import com.appsolute.erba.auth.domain.valueobject.PasswordHash;
import com.appsolute.erba.auth.domain.valueobject.UserId;
import com.appsolute.erba.shared.exception.BaseException;
import com.appsolute.erba.shared.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AuthApplicationServiceTest {

    private UserRepository userRepository;
    private TokenProvider tokenProvider;
    private AuthenticationService authenticationService;
    private RefreshTokenRepository refreshTokenRepository;
    private TokenHasher tokenHasher;
    private AuthApplicationService authApplicationService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        tokenProvider = mock(TokenProvider.class);
        authenticationService = mock(AuthenticationService.class);
        refreshTokenRepository = mock(RefreshTokenRepository.class);
        tokenHasher = mock(TokenHasher.class);

        authApplicationService = new AuthApplicationService(
                userRepository,
                tokenProvider,
                authenticationService,
                refreshTokenRepository,
                tokenHasher
        );
    }

    @Test
    void shouldLoginSuccessfullyAndSaveRefreshToken() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@appsolute.com");
        request.setPassword("password");

        User user = user();

        when(userRepository.findByEmail(any(Email.class)))
                .thenReturn(Optional.of(user));
        when(tokenProvider.generateAccessToken(user))
                .thenReturn("access-token");
        when(tokenProvider.generateRefreshToken(user))
                .thenReturn("refresh-token");
        when(tokenHasher.hash("refresh-token"))
                .thenReturn("refresh-token-hash");

        LoginResponse response = authApplicationService.login(request);

        assertThat(response.getAccessToken()).isEqualTo("access-token");
        assertThat(response.getRefreshToken()).isEqualTo("refresh-token");
        assertThat(response.getExpiresIn()).isEqualTo(3600L);

        verify(authenticationService).authenticate(user, "password");
        verify(userRepository).save(user);
        verify(refreshTokenRepository).revokeActiveTokensByUserId(user.getId());
        verify(refreshTokenRepository).save(
                eq(user.getId()),
                eq("refresh-token-hash"),
                any(LocalDateTime.class)
        );
    }

    @Test
    void shouldThrowInvalidCredentialsWhenUserNotFound() {
        LoginRequest request = new LoginRequest();
        request.setEmail("missing@appsolute.com");
        request.setPassword("password");

        when(userRepository.findByEmail(any(Email.class)))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> authApplicationService.login(request))
                .isInstanceOf(BaseException.class)
                .extracting(ex -> ((BaseException) ex).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_CREDENTIALS);

        verifyNoInteractions(tokenProvider);
        verifyNoInteractions(refreshTokenRepository);
    }

    @Test
    void shouldNotGenerateTokensWhenAuthenticationFails() {
        LoginRequest request = new LoginRequest();
        request.setEmail("admin@appsolute.com");
        request.setPassword("wrong");

        User user = user();

        when(userRepository.findByEmail(any(Email.class)))
                .thenReturn(Optional.of(user));

        doThrow(new BaseException(ErrorCode.INVALID_CREDENTIALS))
                .when(authenticationService)
                .authenticate(user, "wrong");

        assertThatThrownBy(() -> authApplicationService.login(request))
                .isInstanceOf(BaseException.class)
                .extracting(ex -> ((BaseException) ex).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_CREDENTIALS);

        verify(tokenProvider, never()).generateAccessToken(any());
        verify(tokenProvider, never()).generateRefreshToken(any());
        verify(refreshTokenRepository, never()).save(any(), anyString(), any());
    }

    private User user() {
        return new User(
                UserId.newId(),
                Email.of("admin@appsolute.com"),
                PasswordHash.of("hash"),
                true,
                0,
                null
        );
    }
}