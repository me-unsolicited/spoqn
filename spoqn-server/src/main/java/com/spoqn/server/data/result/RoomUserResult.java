package com.spoqn.server.data.result;

import java.time.Instant;
import java.util.UUID;

import com.spoqn.server.data.RoomUser;

import lombok.Data;

@Data
public class RoomUserResult implements Result<RoomUser> {

    private UUID uuid;
    private Instant joined;
    private boolean active;

    @Override
    public RoomUser get() {
        return RoomUser.builder()
                .uuid(uuid)
                .joined(joined)
                .active(active)
                .build();
    }
}
