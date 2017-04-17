package com.spoqn.server.data;

import com.spoqn.server.data.entities.Message;

public interface MessageMapper {

    Message findOne(String id);
}
