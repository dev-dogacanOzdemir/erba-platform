package com.appsolute.erba.auth.domain.exception;

public class InvalidRefreshTokenException extends RuntimeException {

    public InvalidRefreshTokenException() {
        super("Geçersiz refresh token. Lütfen tekrar giriş yapınız.");
    }
}