package com.spoqn.server.data;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import com.spoqn.server.api.json.annotations.DeserializeNullToEmpty;
import com.spoqn.server.api.json.annotations.Reject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@DeserializeNullToEmpty
public class Room {

    String name;
    @Reject Instant created;
    RoomTopic activeTopic;
    @Reject @Singular List<RoomTopic> topics;
    @Singular Set<RoomUser> users;
}
