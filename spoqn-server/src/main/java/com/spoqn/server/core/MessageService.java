package com.spoqn.server.core;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.mybatis.guice.transactional.Transactional;

import com.spoqn.server.data.Message;
import com.spoqn.server.data.access.MessageDao;

@Singleton
@Transactional
public class MessageService {

    @Inject private MessageDao dao;

    public Message create(String loginId, Message message) {

        message = message.toBuilder()
                .loginId(loginId)
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
