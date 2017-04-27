package com.spoqn.server.data;

import java.time.LocalDate;

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
    @Reject LocalDate created;

    @Hide String password;

    public User(String loginId, String displayName, LocalDate created) {
        this(loginId, displayName, created, null);
    }
}
