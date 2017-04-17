package com.spoqn.server.data.mappers;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import com.spoqn.server.data.Message;

public interface MessageMapper {

    Message findOne(@Param("id") UUID id);

    List<Message> findAll();

    UUID create(Message message);
}
