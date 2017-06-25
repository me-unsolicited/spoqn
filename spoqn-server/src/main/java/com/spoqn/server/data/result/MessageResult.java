package com.spoqn.server.data.result;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.spoqn.server.data.Message;

import lombok.Data;

@Data
public class MessageResult implements Result<Message> {

    private UUID id;
    private String text;
    private Instant created;
    private UUID user;
    private List<UUID> attachments;
    private boolean direct;
    private UUID recipient;
    private String room;
    private UUID topic;
    private Set<String> tags;

    @Override
    public Message get() {
        return Message.builder()
                .id(id)
                .text(text)
                .created(created)
                .user(user)
                .attachments(attachments)
                .direct(direct)
                .recipient(recipient)
                .room(room)
                .topic(topic)
                .tags(tags)
                .build();
    }
}
