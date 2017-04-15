package com.spoqn.server.api;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
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

    @Inject private Logins logins;
    @Inject private Users users;

    @PostConstruct
    public void init() {

        // create some development users; temporary obviously

        User frodo = User.builder()
                .username("frodo")
                .displayName("Frodo")
                .email("frodo@spoqn.com")
                .password("password")
                .build();

        User bilbo = User.builder()
                .username("bilbo")
                .displayName("Bilbo")
                .email("bilbo@spoqn.com")
                .password("password")
                .build();

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
