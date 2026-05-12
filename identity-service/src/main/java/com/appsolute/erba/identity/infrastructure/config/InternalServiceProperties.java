package com.appsolute.erba.identity.infrastructure.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "internal")
public record InternalServiceProperties(
        String auditServiceToken
) {
}