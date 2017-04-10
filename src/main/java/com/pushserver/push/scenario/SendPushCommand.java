package com.pushserver.push.scenario;

import com.pushserver.exception.ValidationException;
import org.springframework.util.StringUtils;

public class SendPushCommand {
    private String username;
    private String title;
    private String body;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void validate() {
        if (!StringUtils.hasText(username)) {
            throw new ValidationException("username required");
        }

        if (!StringUtils.hasText(title)) {
            throw new ValidationException("title required");
        }

        if (title.length() > 30) {
            throw new ValidationException("Exceeded title character limit(30)");
        }

        if (!StringUtils.hasText(body)) {
            throw new ValidationException("body required");
        }

    }
}
