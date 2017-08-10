package com.spoqn.server.data;

import java.net.URL;
import java.time.Instant;
import java.util.UUID;

import com.spoqn.server.api.json.annotations.Reject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomTopic {
    @Reject UUID uuid;
    URL url;
    @Reject Instant added;
}
