package com.appsolute.erba.auth.rest.advice;

import com.appsolute.erba.auth.domain.exception.AuthUserAlreadyExistsException;
import com.appsolute.erba.auth.domain.exception.InvalidPasswordResetTokenException;
import com.appsolute.erba.auth.domain.exception.InvalidRefreshTokenException;
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

    @ExceptionHandler(InvalidRefreshTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidRefreshToken() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.fail(
                        new ErrorResponse("INVALID_REFRESH_TOKEN", "Geçersiz veya süresi dolmuş refresh token.")
                ));
    }

    @ExceptionHandler(InvalidPasswordResetTokenException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPasswordResetToken() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(ApiResponse.fail(
                        new ErrorResponse("INVALID_PASSWORD_RESET_TOKEN", "Geçersiz veya süresi dolmuş şifre sıfırlama tokenı.")
                ));
    }

}