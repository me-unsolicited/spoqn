package com.spoqn.server.data.result;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

import com.spoqn.server.data.RoomTopic;

import lombok.Data;

@Data
public class RoomTopicResult implements Result<RoomTopic> {

    private UUID uuid;
    private URL url;
    private Instant added;

    @Override
    public RoomTopic get() {
        return RoomTopic.builder()
                .uuid(uuid)
                .url(url)
                .added(added)
                .build();
    }
}
