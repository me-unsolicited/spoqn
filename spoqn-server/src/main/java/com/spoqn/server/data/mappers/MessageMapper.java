package com.spoqn.server.data.mappers;

import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Param;

import com.spoqn.server.data.Message;
import com.spoqn.server.data.params.MessageParams;
import com.spoqn.server.data.result.Result;

public interface MessageMapper {

    Result<Message> findOne(@Param("user") UUID user, @Param("id") UUID id);

    List<Result<Message>> findBy(@Param("user") UUID user, @Param("params") MessageParams params);

    void createMessage(@Param("user") UUID user, @Param("id") UUID id, @Param("text") String text,
            @Param("room") String room);

    void addRecipient(@Param("message") UUID message, @Param("user") String user);

    void createContent(@Param("content") UUID content, @Param("mimeType") String mimeType,
            @Param("body") byte[] body);

    void attachContent(@Param("message") UUID message, @Param("content") UUID content);

    @Flush
    void flush();
}
