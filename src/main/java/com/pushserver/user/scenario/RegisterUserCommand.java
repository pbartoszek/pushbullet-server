package com.pushserver.user.scenario;

import com.pushserver.exception.ValidationException;
import org.springframework.util.StringUtils;

public class RegisterUserCommand {
    private String username;
    private String accessToken;

    public String getUsername() {
        return username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void validate() {
        if (!StringUtils.hasText(this.getUsername())) {
            throw new ValidationException("username required");
        }

        if (!StringUtils.hasText(this.getAccessToken())) {
            throw new ValidationException("accessToken required");
        }
    }
}
