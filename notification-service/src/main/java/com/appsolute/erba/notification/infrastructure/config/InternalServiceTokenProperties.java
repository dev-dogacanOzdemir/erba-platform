package com.appsolute.erba.notification.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "internal")
public record InternalServiceTokenProperties(
        String serviceToken
) {
}