package com.spoqn.server.data.entities;

import com.spoqn.server.api.json.annotations.Hide;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="password")
public class User {
    private String username;
    private String displayName;
    private String email;

    @Hide
    private String password;
}
