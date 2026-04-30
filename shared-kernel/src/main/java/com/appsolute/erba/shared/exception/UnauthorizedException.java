package com.appsolute.erba.shared.exception;

public class UnauthorizedException extends BaseException {

    public UnauthorizedException(ErrorCode errorCode) {
        super(errorCode);
    }
}