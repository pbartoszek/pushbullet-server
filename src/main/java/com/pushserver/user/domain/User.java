package com.pushserver.user.domain;

import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private final String accessToken;
    private final String username;
    //potentially there could be multiple requests sending pushes for the same user at the same time
    private final AtomicInteger numOfNotificationsPushed = new AtomicInteger();
    private LocalDateTime creationTime;

    public User(String username, String accessToken) {
        Assert.hasText(username, "username");
        Assert.hasText(accessToken, "accessToken");

        this.accessToken = accessToken.trim();
        this.username = username.trim();
        this.creationTime = LocalDateTime.now();
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getUsername() {
        return username;
    }

    public void increaseNotificationsPushed() {
        numOfNotificationsPushed.incrementAndGet();
    }

    public int getNumOfNotificationsPushed() {
        return numOfNotificationsPushed.get();
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "User{" +
               "accessToken='" + accessToken + '\'' +
               ", username='" + username + '\'' +
               ", numOfNotificationsPushed=" + numOfNotificationsPushed +
               ", creationTime=" + creationTime +
               '}';
    }
}
