package com.spoqn.server.api;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.spoqn.server.api.exception.ErrorCode;
import com.spoqn.server.core.UserService;
import com.spoqn.server.core.exceptions.AuthenticationException;
import com.spoqn.server.data.TokenMap;

import lombok.Data;
import lombok.ToString;

@Path("/token")
public class TokenApi {

    private static final String AUTH_PREFIX = "Basic ";
    private static final String AUTH_SEPARATOR = ":";
    private static final String CHALLENGE = "Basic";

    private static final String HEADER_DEVICE_NAME = "X-Device-Name";
    private static final String HEADER_DEVICE_HASH = "X-Device-Hash";

    @Inject private UserService service;
    @Context private SecurityContext sc;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TokenMap login(@HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader,
            @HeaderParam(HEADER_DEVICE_NAME) String deviceName, @HeaderParam(HEADER_DEVICE_HASH) String deviceHash,
            @QueryParam("refresh") boolean refresh) {

        Auth auth = readAuth(authHeader);
        return refresh ? refresh(auth, deviceHash) : authenticate(auth, deviceName, deviceHash);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void logout() {
        service.revoke(sc.getUserPrincipal().getName());
    }

    private TokenMap refresh(Auth auth, String deviceHash) {
        try {
            return service.refresh(auth.getUsername(), auth.getPassword(), deviceHash);
        } catch (AuthenticationException e) {
            throw new NotAuthorizedException(ErrorCode.BAD_REFRESH_TOKEN.name(), e, CHALLENGE);
        }
    }

    private TokenMap authenticate(Auth auth, String deviceName, String deviceHash) {
        try {
            return service.authenticate(auth.getUsername(), auth.getPassword(), deviceName, deviceHash);
        } catch (AuthenticationException e) {
            throw new NotAuthorizedException(ErrorCode.BAD_LOGIN.name(), e, CHALLENGE);
        }
    }

    private Auth readAuth(String auth) {

        if (auth == null || !auth.startsWith(AUTH_PREFIX))
            throw new NotAuthorizedException(ErrorCode.EXPECTED_BASIC_AUTH.name(), CHALLENGE);

        String encodedAuth = auth.substring(AUTH_PREFIX.length()).trim();
        byte[] decodedAuth;

        try {
            decodedAuth = Base64.getDecoder().decode(encodedAuth);
        } catch (IllegalArgumentException e) {
            throw new NotAuthorizedException(ErrorCode.EXPECTED_BASIC_AUTH_BASE64.name(), e, CHALLENGE);
        }

        String userAndPass = new String(decodedAuth, StandardCharsets.UTF_8);
        int sepIndex = userAndPass.indexOf(AUTH_SEPARATOR);
        if (sepIndex < 0)
            throw new NotAuthorizedException(ErrorCode.MALFORMED_BASIC_AUTH.name(), CHALLENGE);

        String username = userAndPass.substring(0, sepIndex);
        String password = userAndPass.substring(sepIndex + 1);

        return new Auth(username, password);
    }

    @Data
    @ToString(exclude = "password")
    private static class Auth {
        private final String username;
        private final String password;
    }
}
