package com.appsolute.erba.auth.domain.exception;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Geçersiz e-mail veya parola girildi.");
    }
}