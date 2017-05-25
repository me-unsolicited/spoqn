package com.spoqn.server.data.mappers;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import com.spoqn.server.data.Message;
import com.spoqn.server.data.params.MessageParams;

public interface MessageMapper {

    Message findOne(@Param("id") UUID id);

    List<Message> findBy(@Param("loginId") String loginId, @Param("params") MessageParams params);

    void create(Message message);

    void createContent(@Param("contentId") UUID contentId, @Param("mimeType") String mimeType,
            @Param("body") byte[] body);
}
