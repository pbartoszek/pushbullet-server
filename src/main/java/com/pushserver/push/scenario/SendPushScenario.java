package com.pushserver.push.scenario;

import com.pushserver.pushbullet.PushBulletClient;
import com.pushserver.pushbullet.PushBulletNotePushRequest;
import com.pushserver.user.domain.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SendPushScenario {

    private final PushBulletClient pushBulletClient;

    public SendPushScenario(PushBulletClient pushBulletClient) {
        this.pushBulletClient = pushBulletClient;
    }

    public SendPushResponse send(SendPushCommand command, User user) {
        PushBulletNotePushRequest pushRequest = new PushBulletNotePushRequest(command.getBody(), command.getTitle());
        Map<String, Object> response = pushBulletClient.send("/pushes", pushRequest, user.getAccessToken());
        user.increaseNotificationsPushed();
        return new SendPushResponse((String) response.get("iden"), user.getNumOfNotificationsPushed());
    }
}
