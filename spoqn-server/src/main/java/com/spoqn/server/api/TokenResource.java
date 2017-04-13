package com.spoqn.server.api;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.spoqn.server.core.Logins;
import com.spoqn.server.core.exceptions.AuthenticationException;
import com.spoqn.server.data.entities.TokenMap;

@Component
@Path("/token")
public class TokenResource {

    private static final String AUTH_PREFIX = "Basic ";
    private static final String AUTH_SEPARATOR = ":";
    private static final String CHALLENGE = "Basic";

    @Resource
    private Logins logins;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public TokenMap login(@HeaderParam(HttpHeaders.AUTHORIZATION) String auth) {

        if (!auth.startsWith(AUTH_PREFIX))
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

        try {
            return logins.authenticate(username, password);
        } catch (AuthenticationException e) {
            throw new NotAuthorizedException("BAD_LOGIN", e, CHALLENGE);
        }
    }
}
