package com.appsolute.erba.auth.infrastructure.security;

import com.appsolute.erba.auth.domain.port.PasswordEncoder;
import com.appsolute.erba.auth.domain.valueobject.PasswordHash;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordEncoderAdapter implements PasswordEncoder {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public boolean matches(String rawPassword, PasswordHash passwordHash) {
        return encoder.matches(rawPassword, passwordHash.getValue());
    }

    @Override
    public PasswordHash encode(String rawPassword) {
        return PasswordHash.of(encoder.encode(rawPassword));
    }
}