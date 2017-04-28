package com.spoqn.server.data.access;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;

import org.mybatis.guice.transactional.Transactional;
import org.mybatis.guice.transactional.Transactional.TxType;

import com.spoqn.server.data.Message;
import com.spoqn.server.data.mappers.MessageMapper;

@Transactional(value = TxType.MANDATORY)
public class MessageDao {

    @Inject private MessageMapper mapper;

    public Message create(Message message) {

        message = message.toBuilder()
                .id(UUID.randomUUID())
                .contentId(UUID.randomUUID())
                .build();

        // first create content; it will be linked to the message
        mapper.createContent(message.getContentId(), MediaType.TEXT_PLAIN,
                message.getContent().getBytes(StandardCharsets.UTF_8));

        // create and return the message itself
        mapper.create(message);
        return mapper.findOne(message.getId());
    }

    public Message find(UUID id) {
        return mapper.findOne(id);
    }

    public List<Message> findAll() {
        return Collections.unmodifiableList(mapper.findAll());
    }
}
