package com.spoqn.server.core;

import java.util.Arrays;
import java.util.List;

import com.spoqn.server.data.entities.Message;

public class Messages {

    public List<Message> getAll() {
        Message message0 = new Message();
        message0.setText("Hello, World!");
        Message message1 = new Message();
        message1.setText("new phone who dis");
        return Arrays.asList(message0, message1);
    }
}
