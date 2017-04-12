package com.spoqn.server.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.spoqn.server.core.exceptions.ExistingLoginException;
import com.spoqn.server.data.entities.User;

@Component
public class Users {

    private final Map<String, User> users = new HashMap<>();

    /**
     * @return 
     * @throws ExistingLoginException
     *             If a user already exists with the provided username
     */
    public User create(User user) {

        String username = user.getUsername();
        if (users.containsKey(username))
            throw new ExistingLoginException();

        users.put(username, user);

        return user;
    }

    public User read(String username) {
        return users.get(username);
    }
}
