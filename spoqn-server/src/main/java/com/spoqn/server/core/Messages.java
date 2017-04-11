package com.spoqn.server.core;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.spoqn.server.data.entities.Message;

@Component
public class Messages {

    public List<Message> getAll() {
        Message message0 = new Message();
        message0.setText("Hello, World!");
        Message message1 = new Message();
        message1.setText("new phone who dis");
        return Arrays.asList(message0, message1);
    }
}
