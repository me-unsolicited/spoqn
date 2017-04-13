package com.spoqn.server.api;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.Resource;
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

import org.springframework.stereotype.Component;

import com.spoqn.server.core.Logins;
import com.spoqn.server.core.exceptions.AuthenticationException;
import com.spoqn.server.data.entities.TokenMap;

import lombok.Data;
import lombok.ToString;

@Component
@Path("/token")
public class TokenResource {

    private static final String AUTH_PREFIX = "Basic ";
    private static final String AUTH_SEPARATOR = ":";
    private static final String CHALLENGE = "Basic";

    @Resource
    private Logins logins;

    @Context
    private SecurityContext sc;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TokenMap login(@HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader,
            @QueryParam("refresh") boolean refresh) {

        Auth auth = readAuth(authHeader);
        return refresh ? refresh(auth) : authenticate(auth);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public void logout() {
        logins.revoke(sc.getUserPrincipal().getName());
    }

    private TokenMap refresh(Auth auth) {
        try {
            return logins.refresh(auth.getUsername(), auth.getPassword());
        } catch (AuthenticationException e) {
            throw new NotAuthorizedException("BAD_REFRESH_TOKEN", e, CHALLENGE);
        }
    }

    private TokenMap authenticate(Auth auth) {
        try {
            return logins.authenticate(auth.getUsername(), auth.getPassword());
        } catch (AuthenticationException e) {
            throw new NotAuthorizedException("BAD_LOGIN", e, CHALLENGE);
        }
    }

    private Auth readAuth(String auth) {

        if (auth == null || !auth.startsWith(AUTH_PREFIX))
            throw new NotAuthorizedException("EXPECTED_BASIC_AUTH", CHALLENGE);

        String encodedAuth = auth.substring(AUTH_PREFIX.length()).trim();
        byte[] decodedAuth;

        try {
            decodedAuth = Base64.getDecoder().decode(encodedAuth);
        } catch (IllegalArgumentException e) {
            throw new NotAuthorizedException("EXPECTED_BASIC_AUTH_BASE64", e, CHALLENGE);
        }

        String userAndPass = new String(decodedAuth, StandardCharsets.UTF_8);
        int sepIndex = userAndPass.indexOf(AUTH_SEPARATOR);
        if (sepIndex < 0)
            throw new NotAuthorizedException("MALFORMED_BASIC_AUTH", CHALLENGE);

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
