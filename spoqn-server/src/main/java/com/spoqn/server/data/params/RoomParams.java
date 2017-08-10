package com.spoqn.server.data.params;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomParams {
    @Singular Set<UUID> users;
    @Singular Set<UUID> topics;
    Instant since;
    Instant until;
    Integer top;
}
