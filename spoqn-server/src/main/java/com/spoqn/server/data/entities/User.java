package com.spoqn.server.data.entities;

import com.spoqn.server.api.json.annotations.Hide;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(exclude = "password")
@Builder(toBuilder = true)
public class User {

    String username;
    String displayName;
    String email;

    @Hide String password;
}
