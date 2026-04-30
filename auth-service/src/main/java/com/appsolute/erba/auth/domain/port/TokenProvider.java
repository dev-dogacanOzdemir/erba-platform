package com.appsolute.erba.auth.domain.port;

import com.appsolute.erba.auth.domain.model.User;

public interface TokenProvider {

    String generateAccessToken(User user);

    String generateRefreshToken(User user);

    boolean validateToken(String token);
}