package com.pushserver.user.domain;

import com.pushserver.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExists extends ApplicationException {

    public UserAlreadyExists() {
        super("User already exists", "user_already_exists", HttpStatus.NOT_ACCEPTABLE);
    }
}
