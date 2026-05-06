package com.appsolute.erba.auth.domain.exception;

public class AuthUserAlreadyExistsException extends RuntimeException {

    public AuthUserAlreadyExistsException(String email) {
        super("Bu mail adresi başka kullanıcı tarafından alınmıştır:  " + email);
    }
}