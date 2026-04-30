package com.appsolute.erba.auth.domain.service;

import com.appsolute.erba.auth.domain.model.User;
import com.appsolute.erba.auth.domain.port.PasswordEncoder;
import com.appsolute.erba.shared.exception.BaseException;
import com.appsolute.erba.shared.exception.ErrorCode;

import java.time.LocalDateTime;

public class AuthenticationService {

    private static final int MAX_FAILED_LOGIN_COUNT = 5;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void authenticate(User user, String rawPassword) {
        if (!user.isActive()) {
            throw new BaseException(ErrorCode.USER_DISABLED);
        }

        if (user.isLocked()) {
            throw new BaseException(ErrorCode.UNAUTHORIZED, "Hesap geçici olarak kilitli");
        }

        boolean passwordMatches = passwordEncoder.matches(
                rawPassword,
                user.getPasswordHash()
        );

        if (!passwordMatches) {
            user.increaseFailedLoginCount();

            if (user.getFailedLoginCount() >= MAX_FAILED_LOGIN_COUNT) {
                user.lockUntil(LocalDateTime.now().plusMinutes(15));
            }

            throw new BaseException(ErrorCode.INVALID_CREDENTIALS);
        }

        user.resetFailedLoginCount();
    }
}