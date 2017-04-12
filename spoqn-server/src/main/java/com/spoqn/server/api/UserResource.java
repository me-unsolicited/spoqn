package com.spoqn.server.api;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

import com.spoqn.server.core.Logins;
import com.spoqn.server.core.Users;
import com.spoqn.server.data.entities.Login;
import com.spoqn.server.data.entities.User;

@Component
@Path("/users")
public class UserResource {

    @Resource
    private Logins logins;

    @Resource
    private Users users;

    @POST
    public User post(User user) {
        logins.create(new Login(user.getUsername(), user.getPassword()));
        return users.create(user);
    }

    @GET
    @Path("/{username}")
    public User get(@PathParam("username") String username) {
        return users.read(username);
    }
}
