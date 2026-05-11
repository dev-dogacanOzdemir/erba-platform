package com.appsolute.erba.shared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

public class JwtTokenValidator {

    private final SecretKey secretKey;

    public JwtTokenValidator(JwtProperties properties) {
        this.secretKey = Keys.hmacShaKeyFor(
                properties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    public AuthenticatedUser validate(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<String> permissions = claims.get(
                "permissions",
                List.class
        );

        return new AuthenticatedUser(
                UUID.fromString(claims.getSubject()),
                claims.get("email", String.class),
                claims.get("role", String.class),
                permissions == null ? List.of() : permissions
        );
    }
}