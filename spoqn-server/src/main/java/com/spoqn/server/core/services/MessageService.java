package com.spoqn.server.core.services;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.mybatis.guice.transactional.Transactional;

import com.spoqn.server.core.SpoqnContext;
import com.spoqn.server.data.Message;
import com.spoqn.server.data.access.MessageDao;

@Transactional
public class MessageService {

    @Inject private SpoqnContext context;
    @Inject private MessageDao dao;

    public Message create(Message message) {

        message = message.toBuilder()
                .loginId(context.getLoginId())
                .build();

        return dao.create(message);
    }

    public List<Message> read() {
        return dao.findAll();
    }

    public Message read(UUID id) {
        return dao.find(id);
    }
}
