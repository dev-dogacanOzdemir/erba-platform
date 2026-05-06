package com.appsolute.erba.auth.rest.advice;

import com.appsolute.erba.auth.domain.exception.AuthUserAlreadyExistsException;
import com.appsolute.erba.shared.response.ApiResponse;
import com.appsolute.erba.shared.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(AuthUserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleAuthUserAlreadyExists(AuthUserAlreadyExistsException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponse.fail(
                        new ErrorResponse("AUTH_USER_ALREADY_EXISTS", "Bu e-posta adresi zaten kayıtlı.")
                ));
    }
}