package com.spoqn.server.core;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mindrot.jbcrypt.BCrypt;
import org.mybatis.guice.transactional.Transactional;

import com.spoqn.server.data.User;
import com.spoqn.server.data.mappers.UserMapper;

@Singleton
@Transactional
public class UserService {

    @Inject private UserMapper mapper;

    public User getUser(String loginId) {
        return mapper.get(loginId);
    }

    public User createUser(User user) {

        // securely generate password hash
        String hash = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        // create the user and store their password hash
        mapper.create(user);
        mapper.createPassword(user.getLoginId(), hash);

        // return the updated user
        return mapper.get(user.getLoginId());
    }
}
