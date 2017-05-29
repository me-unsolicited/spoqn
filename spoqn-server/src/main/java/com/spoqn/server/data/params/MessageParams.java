package com.spoqn.server.data.params;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MessageParams {
    private Set<String> rooms;
    private Set<UUID> topics;
    private Set<String> tags;
    private Set<UUID> users;
    private Boolean direct;
    private Instant since;
    private Instant until;
    private Integer top;
}
