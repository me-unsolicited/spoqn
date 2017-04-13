package com.spoqn.server.core;

import java.security.Principal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.spoqn.server.data.entities.Message;

@Component
public class Messages {

    @Resource
    private Users users;

    private List<Message> messages = new ArrayList<>();
    {
        // temporary until UI controlled
        messages.add(message("frodo", "Frodo", "Hello, World!"));
        messages.add(message("bilbo", "Bilbo", "new phone who dis"));
    }

    private Message message(String user, String displayName, String text) {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setUser(user);
        message.setDisplayName(displayName);
        message.setText(text);
        message.setTimestamp(Instant.now());
        return message;
    }

    public Message create(Principal principal, Message message) {

        String username = principal.getName();

        message.setId(UUID.randomUUID());
        message.setUser(username);
        message.setDisplayName(users.read(username).getDisplayName());
        message.setTimestamp(Instant.now());

        messages.add(message);

        return message;
    }

    public List<Message> read() {
        return Collections.unmodifiableList(messages);
    }
}
