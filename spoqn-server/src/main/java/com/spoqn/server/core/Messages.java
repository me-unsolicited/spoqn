package com.spoqn.server.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Component;

import com.spoqn.server.data.entities.Message;

@Component
public class Messages {

    private List<Message> messages = new ArrayList<>();
    {
        // temporary until UI controlled
        messages.add(message("Hello, World!"));
        messages.add(message("new phone who dis"));
    }

    private Message message(String text) {
        Message message = new Message();
        message.setText(text);
        return message;
    }

    public List<Message> read() {
        return Collections.unmodifiableList(messages);
    }

    public Message create(Message message) {
        messages.add(message);
        return message;
    }
}
