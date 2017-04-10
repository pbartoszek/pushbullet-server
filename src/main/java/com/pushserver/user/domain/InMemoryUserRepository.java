package com.pushserver.user.domain;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryUserRepository implements UserRepository {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    @Override
    public synchronized void addUser(User user) {
        //synchronized due to checking if username and token are unique across all users

        User existingUser = users.get(user.getUsername());
        if (existingUser != null) {
            throw new UserAlreadyExists();
        }

        boolean duplicateTokenFound = isDuplicateTokenFound(user);
        if (duplicateTokenFound) {
            throw new UserAlreadyExists();
        }

        users.put(user.getUsername(), user);
    }

    private boolean isDuplicateTokenFound(User user) {
        return users.values().parallelStream()
            .anyMatch(u -> u.getAccessToken().equals(user.getAccessToken()));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        return Optional.ofNullable(users.get(username));
    }

    @Override
    public Collection<User> getAll() {
        return Collections.unmodifiableCollection(users.values());
    }
}
