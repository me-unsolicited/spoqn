package com.spoqn.server.data.mappers;

import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import com.spoqn.server.data.User;

public interface UserMapper {

    User get(@Param("uuid") UUID uuid);
    User getByLoginId(@Param("loginId") String loginId);
    void create(@Param("user") UUID id, @Param("loginId") String loginId, @Param("displayName") String displayName);
    String getPassHash(@Param("user") UUID user);
    void createPassword(@Param("user") UUID user, @Param("hash") String hash);
    String getDeviceName(@Param("user") UUID user, @Param("hash") String hash);
    void createDevice(@Param("user") UUID user, @Param("deviceName") String deviceName, @Param("hash") String hash);
    void deleteDevice(@Param("user") UUID user, @Param("deviceName") String deviceName);
    void deleteDevices(@Param("user") UUID user);
    String getTokenHash(@Param("user") UUID user, @Param("deviceName") String deviceName);
    void createToken(@Param("user") UUID user, @Param("deviceName") String deviceName, @Param("hash") String hash);
    void updateToken(@Param("user") UUID user, @Param("deviceName") String deviceName, @Param("hash") String hash);
    void deleteToken(@Param("user") UUID user, @Param("deviceName") String deviceName);
    void deleteTokens(@Param("user") UUID user);
}
