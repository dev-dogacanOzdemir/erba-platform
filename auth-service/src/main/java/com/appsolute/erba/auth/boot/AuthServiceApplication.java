package com.appsolute.erba.auth.boot;

import com.appsolute.erba.auth.infrastructure.security.jwt.AuthJwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.appsolute.erba")
@EnableJpaRepositories(basePackages = "com.appsolute.erba.auth.infrastructure.persistence")
@EntityScan(basePackages = "com.appsolute.erba.auth.infrastructure.persistence")
@EnableConfigurationProperties(AuthJwtProperties.class)
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
