package com.pushserver.user.controller;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserVo {
    private final String accessToken;
    private final String username;
    private final int numOfNotificationsPushed;
    private final LocalDateTime creationTime;

    public UserVo(String accessToken, String username, int numOfNotificationsPushed, LocalDateTime creationTime) {
        this.accessToken = accessToken;
        this.username = username;
        this.numOfNotificationsPushed = numOfNotificationsPushed;
        this.creationTime = creationTime;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public int getNumOfNotificationsPushed() {
        return numOfNotificationsPushed;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserVo userVo = (UserVo) o;
        return numOfNotificationsPushed == userVo.numOfNotificationsPushed &&
               Objects.equals(accessToken, userVo.accessToken) &&
               Objects.equals(username, userVo.username) &&
               Objects.equals(creationTime, userVo.creationTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, username, numOfNotificationsPushed, creationTime);
    }

    @Override
    public String toString() {
        return "UserVo{" +
               "accessToken='" + accessToken + '\'' +
               ", username='" + username + '\'' +
               ", numOfNotificationsPushed=" + numOfNotificationsPushed +
               ", creationTime=" + creationTime +
               '}';
    }
}
