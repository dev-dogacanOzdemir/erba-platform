package com.appsolute.erba.auth.domain.service;

import com.appsolute.erba.auth.domain.model.User;
import com.appsolute.erba.auth.domain.port.PasswordEncoder;
import com.appsolute.erba.auth.domain.valueobject.Email;
import com.appsolute.erba.auth.domain.valueobject.PasswordHash;
import com.appsolute.erba.auth.domain.valueobject.UserId;
import com.appsolute.erba.shared.exception.BaseException;
import com.appsolute.erba.shared.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    private PasswordEncoder passwordEncoder;
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        passwordEncoder = mock(PasswordEncoder.class);
        authenticationService = new AuthenticationService(passwordEncoder);
    }

    @Test
    void shouldAuthenticateWhenUserIsActiveAndPasswordMatches() {
        User user = activeUser(0, null);

        when(passwordEncoder.matches("password", user.getPasswordHash()))
                .thenReturn(true);

        authenticationService.authenticate(user, "password");

        assertThat(user.getFailedLoginCount()).isZero();
        assertThat(user.getLockedUntil()).isNull();
    }

    @Test
    void shouldThrowUserDisabledWhenUserIsNotActive() {
        User user = new User(
                UserId.newId(),
                Email.of("admin@appsolute.com"),
                PasswordHash.of("hash"),
                false,
                0,
                null
        );

        assertThatThrownBy(() -> authenticationService.authenticate(user, "password"))
                .isInstanceOf(BaseException.class)
                .extracting(ex -> ((BaseException) ex).getErrorCode())
                .isEqualTo(ErrorCode.USER_DISABLED);
    }

    @Test
    void shouldThrowUnauthorizedWhenUserIsLocked() {
        User user = activeUser(0, LocalDateTime.now().plusMinutes(10));

        assertThatThrownBy(() -> authenticationService.authenticate(user, "password"))
                .isInstanceOf(BaseException.class)
                .extracting(ex -> ((BaseException) ex).getErrorCode())
                .isEqualTo(ErrorCode.UNAUTHORIZED);
    }

    @Test
    void shouldIncreaseFailedLoginCountWhenPasswordDoesNotMatch() {
        User user = activeUser(0, null);

        when(passwordEncoder.matches("wrong", user.getPasswordHash()))
                .thenReturn(false);

        assertThatThrownBy(() -> authenticationService.authenticate(user, "wrong"))
                .isInstanceOf(BaseException.class)
                .extracting(ex -> ((BaseException) ex).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_CREDENTIALS);

        assertThat(user.getFailedLoginCount()).isEqualTo(1);
        assertThat(user.getLockedUntil()).isNull();
    }

    @Test
    void shouldLockUserAfterMaxFailedLoginCount() {
        User user = activeUser(4, null);

        when(passwordEncoder.matches("wrong", user.getPasswordHash()))
                .thenReturn(false);

        assertThatThrownBy(() -> authenticationService.authenticate(user, "wrong"))
                .isInstanceOf(BaseException.class)
                .extracting(ex -> ((BaseException) ex).getErrorCode())
                .isEqualTo(ErrorCode.INVALID_CREDENTIALS);

        assertThat(user.getFailedLoginCount()).isEqualTo(5);
        assertThat(user.getLockedUntil()).isAfter(LocalDateTime.now());
    }

    private User activeUser(int failedLoginCount, LocalDateTime lockedUntil) {
        return new User(
                UserId.newId(),
                Email.of("admin@appsolute.com"),
                PasswordHash.of("hash"),
                true,
                failedLoginCount,
                lockedUntil
        );
    }
}