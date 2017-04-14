package com.spoqn.server.data.entities;

import com.spoqn.server.api.json.annotations.Hide;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
@Builder(toBuilder = true)
public class User {

    private final String username;
    private final String displayName;
    private final String email;

    @Hide private final String password;
}
