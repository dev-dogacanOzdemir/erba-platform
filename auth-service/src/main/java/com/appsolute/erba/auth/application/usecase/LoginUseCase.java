package com.appsolute.erba.auth.application.usecase;

import com.appsolute.erba.auth.application.dto.LoginRequest;
import com.appsolute.erba.auth.application.dto.LoginResponse;

public interface LoginUseCase {

    LoginResponse login(LoginRequest request);
}