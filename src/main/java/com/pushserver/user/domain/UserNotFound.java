package com.pushserver.user.domain;

import com.pushserver.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserNotFound extends ApplicationException {

    public UserNotFound() {
        super("User not found", "user_not_found", HttpStatus.NOT_FOUND);
    }
}
