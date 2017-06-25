package com.spoqn.server.data.access;

import java.util.UUID;

import javax.inject.Inject;

import org.mybatis.guice.transactional.Transactional;
import org.mybatis.guice.transactional.Transactional.TxType;

import com.spoqn.server.data.User;
import com.spoqn.server.data.mappers.UserMapper;

@Transactional(value = TxType.MANDATORY)
public class UserDao {

    @Inject private UserMapper mapper;

    public User create(User user, String passHash) {

        // create the user and store their password hash
        user = user.toBuilder().uuid(UUID.randomUUID()).build();
        mapper.create(user.getUuid(), user.getLoginId(), user.getDisplayName());
        mapper.createPassword(user.getUuid(), passHash);

        // return the updated user
        return mapper.get(user.getUuid());
    }

    public String createDevice(UUID user, String deviceName, String deviceHash) {

        // create the device if it doesn't already exist
        String knownDeviceName = mapper.getDeviceName(user, deviceHash);
        if (knownDeviceName == null) {
            mapper.createDevice(user, deviceName, deviceHash);
            knownDeviceName = deviceName;
        }

        return knownDeviceName;
    }

    public String findDeviceName(UUID user, String deviceHash) {
        return mapper.getDeviceName(user, deviceHash);
    }

    public User find(UUID uuid) {
        return mapper.get(uuid);
    }

    public User find(String loginId) {
        return mapper.getByLoginId(loginId);
    }

    public String findPassHash(UUID user) {
        return mapper.getPassHash(user);
    }

    public String findTokenHash(UUID user, String deviceName) {
        return mapper.getTokenHash(user, deviceName);
    }

    public void updateToken(UUID user, String deviceName, String tokenHash) {

        // put the salted hash in the database; update if already exists
        boolean alreadyExists = mapper.getTokenHash(user, deviceName) != null;
        if (alreadyExists)
            mapper.updateToken(user, deviceName, tokenHash);
        else
            mapper.createToken(user, deviceName, tokenHash);
    }

    public void deleteTokens(UUID user) {
        mapper.deleteTokens(user);
        mapper.deleteDevices(user);
    }

    public void deleteToken(UUID user, String deviceName) {
        mapper.deleteToken(user, deviceName);
        mapper.deleteDevice(user, deviceName);
    }
}
