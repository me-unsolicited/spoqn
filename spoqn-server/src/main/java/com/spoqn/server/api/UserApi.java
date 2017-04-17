package com.spoqn.server.api;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.spoqn.server.api.exception.ErrorCode;
import com.spoqn.server.core.Logins;
import com.spoqn.server.core.Users;
import com.spoqn.server.core.exceptions.ExistingLoginException;
import com.spoqn.server.core.exceptions.InadequatePasswordException;
import com.spoqn.server.data.User;

import lombok.Synchronized;

@Path("/users")
public class UserApi {

    private static boolean initialized = false;

    @Inject private Logins logins;
    @Inject private Users users;

    @PostConstruct
    public void init() {
        initOnce(this);
    }

    @Synchronized
    private static void initOnce(UserApi api) {

        // create some development users; temporary obviously

        if (initialized)
            return;
        initialized = true;

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

        api.post(frodo);
        api.post(bilbo);
    }

    @POST
    public User post(User user) {
        try {
            logins.create(user.getUsername(), user.getPassword());
            return users.create(user);
        } catch (ExistingLoginException e) {
            throw new BadRequestException(ErrorCode.USERNAME_TAKEN.name());
        } catch (InadequatePasswordException e) {
            throw new BadRequestException(ErrorCode.PASSWORD_INADEQUATE.name());
        }
    }

    @GET
    @Path("/{username}")
    public User get(@PathParam("username") String username) {
        return users.read(username);
    }
}
