package com.appsolute.erba.auth.infrastructure.security.jwt;

import com.appsolute.erba.auth.application.port.TokenGenerator;
import com.appsolute.erba.auth.domain.service.AuthRolePermissionResolver;
import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import com.appsolute.erba.shared.security.JwtProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class JwtTokenGenerator implements TokenGenerator {

    private final JwtProperties properties;
    private final SecretKey secretKey;
    private final AuthRolePermissionResolver permissionResolver;

    public JwtTokenGenerator(JwtProperties properties) {
        this.properties = properties;

        this.secretKey = Keys.hmacShaKeyFor(
                properties.secret().getBytes(StandardCharsets.UTF_8)
        );

        this.permissionResolver = new AuthRolePermissionResolver();
    }

    @Override
    public String generateAccessToken(UUID userId, String email, AuthRole role) {
        Instant now = Instant.now();

        List<String> permissions = permissionResolver.resolve(role)
                .stream()
                .map(Enum::name)
                .toList();

        return Jwts.builder()
                .setSubject(userId.toString())
                .claim("email", email)
                .claim("role", role.name())
                .claim("permissions", permissions)
                .setIssuedAt(Date.from(now))
                .setExpiration(
                        Date.from(
                                now.plusSeconds(
                                        properties.accessTokenExpirationSeconds()
                                )
                        )
                )
                .signWith(secretKey, SignatureAlgorithm.HS256)
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

            byte[] hash = digest.digest(
                    rawToken.getBytes(StandardCharsets.UTF_8)
            );

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