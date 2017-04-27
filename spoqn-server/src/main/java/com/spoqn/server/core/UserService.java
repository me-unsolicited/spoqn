package com.spoqn.server.core;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mindrot.jbcrypt.BCrypt;
import org.mybatis.guice.transactional.Transactional;

import com.spoqn.server.core.exceptions.ExistingLoginException;
import com.spoqn.server.core.exceptions.InadequatePasswordException;
import com.spoqn.server.data.User;
import com.spoqn.server.data.mappers.UserMapper;

@Singleton
@Transactional
public class UserService {

    private static final int PASSWORD_MIN_LENGTH = 8;

    @Inject private UserMapper mapper;

    public User getUser(String loginId) {
        return mapper.get(loginId);
    }

    public User createUser(User user) {

        // check for existing login ID
        String loginId = user.getLoginId();
        if (mapper.get(loginId) != null)
            throw new ExistingLoginException();

        // verify password requirements
        String password = user.getPassword();
        if (password.length() < PASSWORD_MIN_LENGTH)
            throw new InadequatePasswordException();

        // securely generate password hash
        String hash = BCrypt.hashpw(password, BCrypt.gensalt());

        // create the user and store their password hash
        mapper.create(user);
        mapper.createPassword(loginId, hash);

        // return the updated user
        return mapper.get(loginId);
    }
}
