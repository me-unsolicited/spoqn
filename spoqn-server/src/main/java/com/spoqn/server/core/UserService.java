package com.spoqn.server.core;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mybatis.guice.transactional.Transactional;

import com.spoqn.server.data.User;
import com.spoqn.server.data.mappers.UserMapper;

@Singleton
@Transactional
public class UserService {

    @Inject
    private UserMapper mapper;

    public User getUser(String loginId) {
        return mapper.get(loginId);
    }
}
