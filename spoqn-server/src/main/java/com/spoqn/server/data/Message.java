package com.spoqn.server.data;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.spoqn.server.api.json.annotations.Reject;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Message {

    @Reject UUID id;
    String text;
    @Reject Instant created;
    @Reject UUID user;
    @Reject List<UUID> attachments;
    @Reject boolean direct;
    UUID recipient;
    String room;
    @Reject UUID topic;
    @Reject Set<String> tags;
}
