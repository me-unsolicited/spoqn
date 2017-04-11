package com.spoqn.server.data.entities;

import java.time.Instant;
import java.util.UUID;

import lombok.Data;

@Data
public class Message {
    private UUID id;
    private String user;
    private String text;
    private Instant timestamp;
}
