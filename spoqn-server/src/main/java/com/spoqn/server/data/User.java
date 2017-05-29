package com.spoqn.server.data;

import java.time.LocalDate;
import java.util.UUID;

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

    UUID uuid;
	String loginId;
    String displayName;
    @Reject LocalDate created;

    @Hide String password;

    public User(UUID uuid, String loginId, String displayName, LocalDate created) {
        this(uuid, loginId, displayName, created, null);
    }
}
