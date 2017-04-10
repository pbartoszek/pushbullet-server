package com.pushserver.user.controller;

import com.pushserver.user.domain.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserVoConverter implements Converter<User, UserVo> {
    @Override
    public UserVo convert(User user) {
        return new UserVo(user.getAccessToken(), user.getUsername(),
            user.getNumOfNotificationsPushed(), user.getCreationTime());
    }
}
