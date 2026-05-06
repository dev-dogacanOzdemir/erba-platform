package com.appsolute.erba.auth.domain.exception;

public class AuthUserNotFoundException extends RuntimeException {

    public AuthUserNotFoundException() {
        super("Kullanıcı bulunamadı.");
    }
}