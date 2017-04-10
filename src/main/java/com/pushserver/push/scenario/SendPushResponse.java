package com.pushserver.push.scenario;

import java.util.Objects;

public class SendPushResponse {
    private final String pushId;
    private final int numOfNotificationsPushed;

    public SendPushResponse(String pushId, int numOfNotificationsPushed) {
        this.pushId = pushId;
        this.numOfNotificationsPushed = numOfNotificationsPushed;
    }

    public String getPushId() {
        return pushId;
    }

    public int getNumOfNotificationsPushed() {
        return numOfNotificationsPushed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendPushResponse that = (SendPushResponse) o;
        return numOfNotificationsPushed == that.numOfNotificationsPushed &&
               Objects.equals(pushId, that.pushId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pushId, numOfNotificationsPushed);
    }

    @Override
    public String toString() {
        return "SendPushResponse{" +
               "pushId='" + pushId + '\'' +
               ", numOfNotificationsPushed=" + numOfNotificationsPushed +
               '}';
    }
}
