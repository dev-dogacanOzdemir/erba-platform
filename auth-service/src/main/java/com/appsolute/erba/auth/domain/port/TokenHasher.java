package com.appsolute.erba.auth.domain.port;

public interface TokenHasher {

    String hash(String token);
}