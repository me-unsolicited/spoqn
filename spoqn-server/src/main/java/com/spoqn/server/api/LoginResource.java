package com.spoqn.server.api;

import java.util.Collections;
import java.util.Map;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.spoqn.server.core.Logins;
import com.spoqn.server.core.exceptions.AuthenticationException;
import com.spoqn.server.data.entities.Login;

@Component
@Path("/login")
public class LoginResource {

    private static final String TOKEN = "token";

    @Resource
    private Logins logins;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void signUp(Login login) {
        logins.create(login);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Map<String, String> login(Login login) {
        try {
            return Collections.singletonMap(TOKEN, logins.authenticate(login));
        } catch (AuthenticationException e) {
            throw new NotAuthorizedException("BAD_LOGIN", e);
        }
    }
}
