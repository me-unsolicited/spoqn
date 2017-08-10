package com.spoqn.server.data.result;

import java.time.Instant;
import java.util.List;
import java.util.Set;

import com.spoqn.server.data.Room;
import com.spoqn.server.data.RoomTopic;
import com.spoqn.server.data.RoomUser;

import lombok.Data;

@Data
public class RoomResult implements Result<Room> {

    private String name;
    private Instant created;
    private Set<Result<RoomUser>> users;
    private Result<RoomTopic> activeTopic;
    private List<Result<RoomTopic>> topics;

    @Override
    public Room get() {
        return Room.builder()
                .name(name)
                .created(created)
                .users(Result.get(users))
                .activeTopic(Result.get(activeTopic))
                .topics(Result.get(topics))
                .build();
    }
}
