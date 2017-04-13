package com.spoqn.server.api;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

import com.spoqn.server.core.Logins;
import com.spoqn.server.core.Users;
import com.spoqn.server.data.entities.User;

@Component
@Path("/users")
public class UserResource {

    @Resource
    private Logins logins;

    @Resource
    private Users users;

    @PostConstruct
    public void init() {

        // create some development users; temporary obviously

        User frodo = new User();
        frodo.setUsername("frodo");
        frodo.setDisplayName("Frodo");
        frodo.setEmail("frodo@spoqn.com");
        frodo.setPassword("password");

        User bilbo = new User();
        bilbo.setUsername("bilbo");
        bilbo.setDisplayName("Bilbo");
        bilbo.setEmail("bilbo@spoqn.com");
        bilbo.setPassword("password");

        post(frodo);
        post(bilbo);
    }

    @POST
    public User post(User user) {
        logins.create(user.getUsername(), user.getPassword());
        return users.create(user);
    }

    @GET
    @Path("/{username}")
    public User get(@PathParam("username") String username) {
        return users.read(username);
    }
}
