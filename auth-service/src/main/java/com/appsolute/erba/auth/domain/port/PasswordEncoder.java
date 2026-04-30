package com.appsolute.erba.auth.domain.port;

import com.appsolute.erba.auth.domain.valueobject.PasswordHash;

public interface PasswordEncoder {

    boolean matches(String rawPassword, PasswordHash passwordHash);

    PasswordHash encode(String rawPassword);
}