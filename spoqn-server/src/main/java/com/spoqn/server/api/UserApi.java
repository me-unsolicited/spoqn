package com.spoqn.server.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.spoqn.server.api.exception.ErrorCode;
import com.spoqn.server.core.UserService;
import com.spoqn.server.data.User;

@Path("/users")
public class UserApi {

    @Inject
    private UserService service;

    @GET
    @Path("/{loginId}")
    @Produces(MediaType.APPLICATION_JSON)
    public User get(@PathParam("loginId") String loginId) {

        User user = service.getUser(loginId);
        if (user == null)
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND.name());

        return user;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User post(User user) {

        return service.createUser(user);
    }
}
