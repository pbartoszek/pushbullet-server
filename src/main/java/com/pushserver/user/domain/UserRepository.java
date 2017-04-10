package com.pushserver.user.domain;

import com.pushserver.user.domain.User;

import java.util.Collection;
import java.util.Optional;

public interface UserRepository {
    void addUser(User user);

    Optional<User> getByUsername(String username);

    Collection<User> getAll();
}
