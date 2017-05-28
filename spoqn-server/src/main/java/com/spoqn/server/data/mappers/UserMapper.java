package com.spoqn.server.data.mappers;

import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import com.spoqn.server.data.User;

public interface UserMapper {

    User get(@Param("uuid") UUID uuid);
    User getByLoginId(@Param("loginId") String loginId);
    void create(@Param("user") User user);
    String getPassHash(@Param("loginId") String loginId);
    void createPassword(@Param("loginId") String loginId, @Param("hash") String hash);
    String getDeviceName(@Param("loginId") String loginId, @Param("hash") String hash);
    void createDevice(@Param("loginId") String loginId, @Param("deviceName") String deviceName, @Param("hash") String hash);
    void deleteDevice(@Param("loginId") String loginId, @Param("deviceName") String deviceName);
    void deleteDevices(@Param("loginId") String loginId);
    String getTokenHash(@Param("loginId") String loginId, @Param("deviceName") String deviceName);
    void createToken(@Param("loginId") String loginId, @Param("deviceName") String deviceName, @Param("hash") String hash);
    void updateToken(@Param("loginId") String loginId, @Param("deviceName") String deviceName, @Param("hash") String hash);
    void deleteToken(@Param("loginId") String loginId, @Param("deviceName") String deviceName);
    void deleteTokens(@Param("loginId") String loginId);
}
