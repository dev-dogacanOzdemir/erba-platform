package com.appsolute.erba.auth.infrastructure.config;

import com.appsolute.erba.auth.application.service.AuthApplicationService;
import com.appsolute.erba.auth.application.usecase.LoginUseCase;
import com.appsolute.erba.auth.domain.port.*;
import com.appsolute.erba.auth.domain.service.AuthenticationService;
import com.appsolute.erba.auth.infrastructure.persistence.SpringDataRefreshTokenJpaRepository;
import com.appsolute.erba.auth.infrastructure.security.JwtProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class BeanConfig {

    @Bean
    public AuthenticationService authenticationService(PasswordEncoder passwordEncoder) {
        return new AuthenticationService(passwordEncoder);
    }

    @Bean
    public LoginUseCase loginUseCase(
            UserRepository userRepository,
            TokenProvider tokenProvider,
            AuthenticationService authenticationService,
            RefreshTokenRepository refreshTokenRepository,
            TokenHasher tokenHasher) {
        return new AuthApplicationService(
                userRepository,
                tokenProvider,
                authenticationService,
                refreshTokenRepository,
                tokenHasher
        );
    }
}