package com.spoqn.server.data;

import java.time.Instant;

import com.spoqn.server.api.json.annotations.Hide;
import com.spoqn.server.api.json.annotations.Reject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(exclude = "password")
@Builder(toBuilder = true)
@AllArgsConstructor
public class User {

    String loginId;
    String displayName;
    @Reject Instant created;

    @Hide String password;

    public User(String loginId, String displayName, Instant created) {
        this(loginId, displayName, created, null);
    }
}
