package com.pushserver.pushbullet;

public class PushBulletNotePushRequest {
    private final String body;
    private final String title;

    public PushBulletNotePushRequest(String body, String title) {
        this.body = body;
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return "note";
    }
}
