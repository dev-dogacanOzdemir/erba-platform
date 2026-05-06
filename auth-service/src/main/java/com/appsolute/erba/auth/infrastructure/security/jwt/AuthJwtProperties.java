package com.appsolute.erba.auth.infrastructure.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "auth.jwt")
public record AuthJwtProperties(
        String secret,
        long accessTokenExpirationSeconds
) {
}