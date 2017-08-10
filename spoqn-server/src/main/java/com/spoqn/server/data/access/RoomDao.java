package com.spoqn.server.data.access;

import java.net.URL;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.mybatis.guice.transactional.Transactional;
import org.mybatis.guice.transactional.Transactional.TxType;

import com.spoqn.server.data.Room;
import com.spoqn.server.data.RoomUser;
import com.spoqn.server.data.mappers.RoomMapper;
import com.spoqn.server.data.params.RoomParams;
import com.spoqn.server.data.result.Result;

@Transactional(value = TxType.MANDATORY)
public class RoomDao {

    @Inject private RoomMapper mapper;

    public Room create(UUID user, Room room) {

        Instant now = Instant.now();

        // create the room
        mapper.createRoom(room.getName(), now);

        // add invited users
        for (RoomUser invitee : room.getUsers())
            mapper.inviteUser(room.getName(), invitee.getUuid(), now, invitee.isActive());

        // set the active topic
        if (room.getActiveTopic() != null) {
            URL url = room.getActiveTopic().getUrl();
            UUID topic = mapper.findTopic(url);
            if (topic == null) {
                topic = UUID.randomUUID();
                mapper.createTopic(topic, url);
            }
            mapper.setTopic(room.getName(), topic, now);
        }

        mapper.flush();
        return Result.get(mapper.findOne(user, room.getName()));
    }

    public Room find(UUID user, String name) {
        return Result.get(mapper.findOne(user, name));
    }

    public Room find(String name) {
        return Result.get(mapper.findOneGlobally(name));
    }

    public List<Room> findBy(UUID user, RoomParams params) {
        return Result.get(mapper.findBy(user, params));
    }
}
