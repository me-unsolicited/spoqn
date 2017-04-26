package com.spoqn.server.data.mappers;

import org.apache.ibatis.annotations.Param;

import com.spoqn.server.data.User;

public interface UserMapper {

    User get(@Param("loginId") String loginId);
}
