package com.appsolute.erba.auth.infrastructure.security;

import com.appsolute.erba.auth.domain.valueobject.PasswordHash;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class BcryptPasswordEncoderAdapterTest {

    private final BcryptPasswordEncoderAdapter adapter =
            new BcryptPasswordEncoderAdapter();

    @Test
    void shouldEncodePasswordAndMatchRawPassword() {
        PasswordHash hash = adapter.encode("password");

        assertThat(hash.getValue()).isNotBlank();
        assertThat(hash.getValue()).isNotEqualTo("password");
        assertThat(adapter.matches("password", hash)).isTrue();
    }

    @Test
    void shouldNotMatchWrongPassword() {
        PasswordHash hash = adapter.encode("password");

        assertThat(adapter.matches("wrong-password", hash)).isFalse();
    }
}