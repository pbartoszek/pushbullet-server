package com.pushserver.pushbullet;

import com.pushserver.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class PushBulletException extends ApplicationException {
    public PushBulletException(String message, String errorCode, HttpStatus httpStatus) {
        super(message, errorCode, httpStatus);
    }
}
