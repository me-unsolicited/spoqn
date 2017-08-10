package com.spoqn.server.data.mappers;

import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.apache.ibatis.annotations.Flush;
import org.apache.ibatis.annotations.Param;

import com.spoqn.server.data.Room;
import com.spoqn.server.data.params.RoomParams;
import com.spoqn.server.data.result.Result;

public interface RoomMapper {

    Result<Room> findOne(@Param("user") UUID user, @Param("name") String name);

    Result<Room> findOneGlobally(@Param("name") String name);

    List<Result<Room>> findBy(@Param("user") UUID user, @Param("params") RoomParams params);

    UUID findTopic(@Param("url") URL url);

    void createRoom(@Param("name") String name, @Param("created") Instant created);

    void inviteUser(@Param("room") String roomName, @Param("invitee") UUID invitee, @Param("joined") Instant joined, @Param("active") boolean active);

    void createTopic(@Param("topic") UUID topic, @Param("url") URL url);

    void setTopic(@Param("room") String roomName, @Param("topic") UUID topic, @Param("added") Instant added);

    @Flush
    void flush();
}
