package com.spoqn.server.data.entities;

import java.time.Instant;
import java.util.UUID;

import com.spoqn.server.api.json.annotations.Reject;

import lombok.Data;

@Data
public class Message {
    @Reject private UUID id;
    @Reject private String user;
    @Reject private String displayName;
    private String text;
    @Reject private Instant timestamp;
}
