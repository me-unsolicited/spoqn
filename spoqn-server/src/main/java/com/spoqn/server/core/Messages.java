package com.spoqn.server.core;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.spoqn.server.data.Message;
import com.spoqn.server.data.mappers.MessageMapper;

@Singleton
public class Messages {

    @Inject private MessageMapper mapper;

    public Message create(String username, Message message) {
        UUID id = mapper.create(message.toBuilder().user(username).build());
        return mapper.findOne(id);
    }

    public List<Message> read() {
        return Collections.unmodifiableList(mapper.findAll());
    }

    public Message read(UUID id) {
        return mapper.findOne(id);
    }
}
