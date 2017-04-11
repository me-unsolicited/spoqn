package com.spoqn.server.data.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="password")
public class Login {
    private final String username;
    private final String password;
}
