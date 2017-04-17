package com.spoqn.server.core;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.spoqn.server.data.Message;
import com.spoqn.server.data.mappers.MessageMapper;

@Singleton
public class Messages {

    @Inject private Users users;
    @Inject private MessageMapper mapper;

    private HashMap<UUID, Message> messages = new LinkedHashMap<>();
    {
        // temporary until UI controlled

        Message message0 = message("frodo", "Frodo", "Hello, World!");
        Message message1 = message("bilbo", "Bilbo", "new phone who dis");

        messages.put(message0.getId(), message0);
        messages.put(message1.getId(), message1);
    }

    private Message message(String user, String displayName, String text) {
        return Message.builder()
                .id(UUID.randomUUID())
                .user(user)
                .displayName(displayName)
                .text(text)
                .timestamp(Instant.now())
                .build();
    }

    public Message create(String username, Message message) {

        message = message.toBuilder()
                .id(UUID.randomUUID())
                .user(username)
                .displayName(users.read(username).getDisplayName())
                .timestamp(Instant.now())
                .build();

        messages.put(message.getId(), message);
        return message;
    }

    public List<Message> read() {
        return Collections.unmodifiableList(new ArrayList<>(messages.values()));
    }

    public Message read(UUID id) {
        return mapper.findOne(id.toString());
    }
}
