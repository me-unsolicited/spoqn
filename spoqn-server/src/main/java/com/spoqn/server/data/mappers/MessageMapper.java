package com.spoqn.server.data.mappers;

import java.util.List;
import java.util.UUID;

import com.spoqn.server.data.Message;

public interface MessageMapper {

    Message findOne(String id);

    List<Message> findAll();

    UUID create(Message message);
}
