package com.spoqn.server.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.spoqn.server.data.entities.Message;

@Component
public class Messages {

    private List<Message> messages = new ArrayList<>();
    {
        // temporary until UI controlled
        messages.add(message(UUID.randomUUID(), "frodo", "Hello, World!"));
        messages.add(message(UUID.randomUUID(), "bilbo", "new phone who dis"));
    }

    private Message message(UUID id, String user, String text) {
        Message message = new Message();
        message.setId(id);
        message.setUser(user);
        message.setText(text);
        return message;
    }

    public Message create(Message message) {
        message.setId(UUID.randomUUID());
        messages.add(message);
        return message;
    }

    public List<Message> read() {
        return Collections.unmodifiableList(messages);
    }
}
