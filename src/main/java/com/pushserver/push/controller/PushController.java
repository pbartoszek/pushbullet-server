package com.pushserver.push.controller;

import com.pushserver.push.scenario.SendPushCommand;
import com.pushserver.push.scenario.SendPushResponse;
import com.pushserver.push.scenario.SendPushScenario;
import com.pushserver.user.domain.User;
import com.pushserver.user.domain.UserNotFound;
import com.pushserver.user.domain.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PushController {

    private final UserRepository userRepository;
    private final SendPushScenario sendPushScenario;

    public PushController(UserRepository userRepository,
                          SendPushScenario sendPushScenario) {
        this.userRepository = userRepository;
        this.sendPushScenario = sendPushScenario;
    }

    @PostMapping("/push")
    @ResponseBody
    SendPushResponse sendPush(@RequestBody SendPushCommand sendPushCommand) {
        sendPushCommand.validate();
        User user = userRepository.getByUsername(sendPushCommand.getUsername()).orElseThrow(UserNotFound::new);
        return sendPushScenario.send(sendPushCommand, user);
    }
}