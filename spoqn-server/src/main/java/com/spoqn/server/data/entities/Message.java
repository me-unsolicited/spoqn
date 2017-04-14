package com.spoqn.server.data.entities;

import java.time.Instant;
import java.util.UUID;

import com.spoqn.server.api.json.annotations.Reject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Message {

    private final String text;

    @Reject private final UUID id;
    @Reject private final String user;
    @Reject private final String displayName;
    @Reject private final Instant timestamp;
}
