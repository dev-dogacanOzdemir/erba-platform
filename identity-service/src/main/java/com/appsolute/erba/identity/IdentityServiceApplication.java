package com.appsolute.erba.identity;

import com.appsolute.erba.identity.infrastructure.config.InternalServiceProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.appsolute.erba")
@EnableJpaRepositories(basePackages = "com.appsolute.erba.identity.infrastructure.persistence.repository")
@EntityScan(basePackages = "com.appsolute.erba.identity.infrastructure.persistence.entity")
@EnableConfigurationProperties(InternalServiceProperties.class)
public class IdentityServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IdentityServiceApplication.class, args);
    }

}
