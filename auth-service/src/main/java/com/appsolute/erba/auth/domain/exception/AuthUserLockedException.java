package com.appsolute.erba.auth.domain.exception;

public class AuthUserLockedException extends RuntimeException {

    public AuthUserLockedException() {
        super("Kullanıcı hesabı kilitlenmiştir. Lütfen sistem yöneticisi ile iletişime geçiniz.");
    }
}