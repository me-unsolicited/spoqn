package com.spoqn.server.data.access;

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
        mapper.create(user);
        mapper.createPassword(user.getLoginId(), passHash);

        // return the updated user
        return mapper.get(user.getLoginId());
    }

    public String createDevice(String loginId, String deviceName, String deviceHash) {

        // create the device if it doesn't already exist
        String knownDeviceName = mapper.getDeviceName(loginId, deviceHash);
        if (knownDeviceName == null) {
            mapper.createDevice(loginId, deviceName, deviceHash);
            knownDeviceName = deviceName;
        }

        return knownDeviceName;
    }

    public String findDeviceName(String loginId, String deviceHash) {
        return mapper.getDeviceName(loginId, deviceHash);
    }

    public User find(String loginId) {
        return mapper.get(loginId);
    }

    public String findPassHash(String loginId) {
        return mapper.getPassHash(loginId);
    }

    public String findTokenHash(String loginId, String deviceName) {
        return mapper.getTokenHash(loginId, deviceName);
    }

    public void updateToken(String loginId, String deviceName, String tokenHash) {

        // put the salted hash in the database; update if already exists
        boolean alreadyExists = mapper.getTokenHash(loginId, deviceName) != null;
        if (alreadyExists)
            mapper.updateToken(loginId, deviceName, tokenHash);
        else
            mapper.createToken(loginId, deviceName, tokenHash);
    }

    public void deleteTokens(String loginId) {
        mapper.deleteTokens(loginId);
        mapper.deleteDevices(loginId);
    }

    public void deleteToken(String loginId, String deviceName) {
        mapper.deleteToken(loginId, deviceName);
        mapper.deleteDevice(loginId, deviceName);
    }
}
