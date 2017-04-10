package com.pushserver.user.controller;

import com.pushserver.user.domain.User;
import com.pushserver.user.domain.UserRepository;
import com.pushserver.user.scenario.RegisterUserCommand;
import com.pushserver.user.scenario.RegisterUserScenario;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
public class UserController {

    private final UserRepository userRepository;
    private final RegisterUserScenario newUserScenario;
    private final Converter<User, UserVo> userVoConverter;

    public UserController(UserRepository userRepository,
                          RegisterUserScenario newUserScenario,
                          UserVoConverter userVoConverter) {
        this.userRepository = userRepository;
        this.newUserScenario = newUserScenario;
        this.userVoConverter = userVoConverter;
    }

    @PostMapping("/users")
    @ResponseBody
    UserVo createUser(@RequestBody RegisterUserCommand newUserCommand) {
        newUserCommand.validate();

        User newUser = newUserScenario.register(newUserCommand.getUsername(), newUserCommand.getAccessToken());
        return userVoConverter.convert(newUser);
    }

    @GetMapping("/users")
    @ResponseBody
    Collection<UserVo> getUsers() {
        Collection<User> users = userRepository.getAll();
        return users.parallelStream()
            .map(userVoConverter::convert)
            .sorted(Comparator.comparing(UserVo::getCreationTime).reversed())
            .collect(Collectors.toList());
    }
}