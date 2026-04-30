package com.appsolute.erba.auth.infrastructure.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class Sha256TokenHasherTest {

    private final Sha256TokenHasher tokenHasher = new Sha256TokenHasher();

    @Test
    void shouldGenerateSameHashForSameToken() {
        String hash1 = tokenHasher.hash("refresh-token");
        String hash2 = tokenHasher.hash("refresh-token");

        assertThat(hash1).isEqualTo(hash2);
    }

    @Test
    void shouldGenerateDifferentHashForDifferentTokens() {
        String hash1 = tokenHasher.hash("refresh-token-1");
        String hash2 = tokenHasher.hash("refresh-token-2");

        assertThat(hash1).isNotEqualTo(hash2);
    }

    @Test
    void shouldReturnSha256HexLength() {
        String hash = tokenHasher.hash("refresh-token");

        assertThat(hash).hasSize(64);
    }
}