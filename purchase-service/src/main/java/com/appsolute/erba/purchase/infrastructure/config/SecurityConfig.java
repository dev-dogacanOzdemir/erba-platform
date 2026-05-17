package com.appsolute.erba.purchase.infrastructure.config;

import com.appsolute.erba.shared.security.JwtAuthenticationFilter;
import com.appsolute.erba.shared.security.SharedSecurityBeansConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@Import(SharedSecurityBeansConfig.class)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/v1/purchase-requests"
                        ).hasAuthority("PURCHASE_REQUEST_CREATE")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/purchase-requests/me"
                        ).hasAuthority("PURCHASE_REQUEST_READ")

                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/v1/purchase-requests"
                        ).hasAuthority("PURCHASE_REQUEST_REVIEW")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/purchase-requests/*/approve"
                        ).hasAuthority("PURCHASE_REQUEST_APPROVE")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/v1/purchase-requests/*/reject"
                        ).hasAuthority("PURCHASE_REQUEST_REJECT")

                        .anyRequest()
                        .authenticated()
                )
                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173",
                "http://localhost:5174"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "PATCH",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}