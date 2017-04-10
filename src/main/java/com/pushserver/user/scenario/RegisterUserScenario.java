package com.pushserver.user.scenario;

import com.pushserver.user.domain.User;
import com.pushserver.user.domain.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserScenario {

    private final UserRepository userRepository;

    public RegisterUserScenario(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(String username, String accessToken) {
        User user = new User(username, accessToken);
        userRepository.addUser(user);
        return user;
    }
}
