package com.spoqn.server.data.entities;

import com.google.gson.annotations.Expose;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="password")
public class User {
    private String username;
    private String displayName;
    private String email;

    @Expose(serialize=false)
    private String password;
}
