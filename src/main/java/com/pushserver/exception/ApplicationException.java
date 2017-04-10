package com.pushserver.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {
    private HttpStatus httpStatus;
    private final String errorCode;

    public ApplicationException(String message, String errorCode, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
