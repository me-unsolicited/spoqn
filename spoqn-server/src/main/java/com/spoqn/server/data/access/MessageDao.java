package com.spoqn.server.data.access;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.mybatis.guice.transactional.Transactional;
import org.mybatis.guice.transactional.Transactional.TxType;

import com.spoqn.server.data.Message;
import com.spoqn.server.data.mappers.MessageMapper;
import com.spoqn.server.data.params.MessageParams;
import com.spoqn.server.data.result.Result;

@Transactional(value = TxType.MANDATORY)
public class MessageDao {

    @Inject private MessageMapper mapper;

    public Message create(Message message) {

        message = message.toBuilder()
                .id(UUID.randomUUID())
                .created(Instant.now())
                .build();

        // create the message
        mapper.createMessage(message.getUser(), message.getId(), message.getText(), message.getRoom());

        // create content
        // TODO create content here? in a separate call?

        // attach content
        if (message.getAttachments() != null)
            for (UUID attachmentId : message.getAttachments())
                mapper.attachContent(message.getId(), attachmentId);

        mapper.flush();
        return Result.get(mapper.findOne(message.getUser(), message.getId()));
    }

    public Message find(UUID user, UUID id) {
        return Result.get(mapper.findOne(user, id));
    }

    public List<Message> findBy(UUID user, MessageParams params) {
        return Result.get(mapper.findBy(user, params));
    }
}
