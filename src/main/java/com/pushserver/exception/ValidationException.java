package com.pushserver.exception;


import com.pushserver.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class ValidationException extends ApplicationException {
    public ValidationException(String message) {
        super(message, "validation", HttpStatus.BAD_REQUEST);
    }
}
