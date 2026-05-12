package com.appsolute.erba.auth.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "internal")
public record InternalServiceProperties(
        String auditServiceToken
) {
}