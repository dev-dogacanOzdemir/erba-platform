package com.appsolute.erba.auth.infrastructure.security.jwt;

import com.appsolute.erba.auth.application.port.TokenValidator;
import com.appsolute.erba.auth.domain.valueobject.AuthRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class JwtTokenValidator implements TokenValidator {

    private final SecretKey secretKey;

    public JwtTokenValidator(AuthJwtProperties properties) {
        this.secretKey = Keys.hmacShaKeyFor(
                properties.secret().getBytes(StandardCharsets.UTF_8)
        );
    }

    @Override
    public ValidatedToken validateAccessToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        UUID userId = UUID.fromString(claims.getSubject());
        String email = claims.get("email", String.class);
        AuthRole role = AuthRole.valueOf(claims.get("role", String.class));

        return new ValidatedToken(
                userId,
                email,
                role
        );
    }
}