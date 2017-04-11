package com.spoqn.server.core;

import java.security.Principal;
import java.time.Instant;
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
        messages.add(message("frodo", "Hello, World!"));
        messages.add(message("bilbo", "new phone who dis"));
    }

    private Message message(String user, String text) {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setUser(user);
        message.setText(text);
        message.setTimestamp(Instant.now());
        return message;
    }

    public Message create(Principal principal, Message message) {
        message.setId(UUID.randomUUID());
        message.setUser(principal.getName());
        message.setTimestamp(Instant.now());
        messages.add(message);
        return message;
    }

    public List<Message> read() {
        return Collections.unmodifiableList(messages);
    }
}
