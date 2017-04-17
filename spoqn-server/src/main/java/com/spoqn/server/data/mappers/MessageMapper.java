package com.spoqn.server.data.mappers;

import com.spoqn.server.data.Message;

public interface MessageMapper {

    Message findOne(String id);
}
