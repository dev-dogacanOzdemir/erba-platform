package com.appsolute.erba.auth.infrastructure.config;

import com.appsolute.erba.auth.domain.model.User;
import com.appsolute.erba.auth.domain.port.PasswordEncoder;
import com.appsolute.erba.auth.domain.port.UserRepository;
import com.appsolute.erba.auth.domain.valueobject.Email;
import com.appsolute.erba.auth.domain.valueobject.UserId;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevDataSeeder {

    @Bean
    public CommandLineRunner seedAdminUser(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            Email email = Email.of("admin@appsolute.com");

            if (userRepository.findByEmail(email).isPresent()) {
                return;
            }

            User admin = new User(
                    UserId.newId(),
                    email,
                    passwordEncoder.encode("password"),
                    true,
                    0,
                    null
            );

            userRepository.save(admin);
        };
    }
}