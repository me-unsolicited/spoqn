package com.spoqn.server.data;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.spoqn.server.api.json.annotations.Reject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Room {

    private Room() {

        // work-around to prevent GSON from initializing collections to null

        name = null;
        created = null;
        activeTopic = null;
        topics = Collections.emptyList();
        users = Collections.emptySet();
    }

    String name;
    @Reject Instant created;
    RoomTopic activeTopic;
    @Reject @Singular List<RoomTopic> topics;
    @Singular Set<RoomUser> users;
}
