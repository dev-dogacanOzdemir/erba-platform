package com.appsolute.erba.auth.infrastructure.security.jwt;

import com.appsolute.erba.auth.application.port.TokenGenerator;
import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtTokenGenerator implements TokenGenerator {

    // 256-bit secure key
    private static final SecretKey SECRET_KEY =
            Keys.secretKeyFor(SignatureAlgorithm.HS256);

    private static final long ACCESS_TOKEN_EXPIRATION_SECONDS = 900;

    @Override
    public String generateAccessToken(UUID userId, String email, AuthRole role) {
        Instant now = Instant.now();

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("role", role.name())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ACCESS_TOKEN_EXPIRATION_SECONDS)))
                .signWith(SECRET_KEY)
                .compact();
    }

    @Override
    public String generateRefreshToken() {
        return UUID.randomUUID().toString() + UUID.randomUUID();
    }

    @Override
    public String generatePasswordResetToken() {
        return UUID.randomUUID().toString() + UUID.randomUUID();
    }

    @Override
    public String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(rawToken.getBytes());

            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();

        } catch (Exception e) {
            throw new RuntimeException("Token hashing failed", e);
        }
    }
}