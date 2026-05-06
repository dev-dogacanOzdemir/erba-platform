package com.appsolute.erba.auth.domain.exception;

public class InvalidPasswordResetTokenException extends RuntimeException {

    public InvalidPasswordResetTokenException() {
        super("Geçersiz parola sıfırlama token'ı. Lütfen tekrar deneyiniz.");
    }
}