package com.appsolute.erba.shared.exception;

import com.appsolute.erba.shared.response.ApiResponse;
import com.appsolute.erba.shared.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ApiResponse<Void>> handleBaseException(BaseException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getErrorCode().getCode(),
                exception.getMessage()
        );

        return ResponseEntity
                .status(resolveStatus(exception.getErrorCode()))
                .body(ApiResponse.fail(errorResponse));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception exception) {

        log.error("Unexpected error occurred", exception);

        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.INTERNAL_ERROR.getCode(),
                ErrorCode.INTERNAL_ERROR.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.fail(errorResponse));
    }

    private HttpStatus resolveStatus(ErrorCode errorCode) {
        return switch (errorCode) {
            case INVALID_CREDENTIALS, UNAUTHORIZED, TOKEN_EXPIRED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case USER_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case EMAIL_ALREADY_EXISTS -> HttpStatus.CONFLICT;
            case VALIDATION_ERROR -> HttpStatus.BAD_REQUEST;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException exception) {
        ErrorResponse errorResponse = new ErrorResponse(
                ErrorCode.VALIDATION_ERROR.getCode(),
                ErrorCode.VALIDATION_ERROR.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.fail(errorResponse));
    }
}