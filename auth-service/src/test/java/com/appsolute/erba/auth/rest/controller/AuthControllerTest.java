package com.appsolute.erba.auth.rest.controller;

import com.appsolute.erba.auth.application.dto.LoginResponse;
import com.appsolute.erba.auth.application.usecase.LoginUseCase;
import com.appsolute.erba.shared.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class AuthControllerTest {

    private MockMvc mockMvc;
    private LoginUseCase loginUseCase;

    @BeforeEach
    void setUp() {
        loginUseCase = mock(LoginUseCase.class);

        AuthController authController = new AuthController(loginUseCase);

        mockMvc = MockMvcBuilders
                .standaloneSetup(authController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldLoginSuccessfully() throws Exception {
        when(loginUseCase.login(any()))
                .thenReturn(new LoginResponse(
                        "access-token",
                        "refresh-token",
                        3600L
                ));

        String body = """
                {
                  "email": "admin@appsolute.com",
                  "password": "password"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.accessToken").value("access-token"))
                .andExpect(jsonPath("$.data.refreshToken").value("refresh-token"))
                .andExpect(jsonPath("$.data.expiresIn").value(3600))
                .andExpect(jsonPath("$.error").doesNotExist());
    }

    @Test
    void shouldReturnBadRequestWhenEmailIsInvalid() throws Exception {
        String body = """
                {
                  "email": "invalid-email",
                  "password": "password"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsBlank() throws Exception {
        String body = """
                {
                  "email": "admin@appsolute.com",
                  "password": ""
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest());
    }
}