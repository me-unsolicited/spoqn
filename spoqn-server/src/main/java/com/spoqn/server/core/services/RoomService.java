package com.spoqn.server.core.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.mybatis.guice.transactional.Transactional;

import com.spoqn.server.core.SpoqnContext;
import com.spoqn.server.core.exceptions.RoomNameTakenException;
import com.spoqn.server.data.Room;
import com.spoqn.server.data.RoomUser;
import com.spoqn.server.data.access.RoomDao;
import com.spoqn.server.data.params.RoomParams;

@Transactional
public class RoomService {

    @Inject private SpoqnContext context;
    @Inject private RoomDao dao;

    /**
     * Create a room.
     * 
     * @param room
     *            Room
     * @return Created room
     * @throws RoomNameTakenException
     */
    public Room create(Room room) {

        if (dao.find(room.getName()) != null)
            throw new RoomNameTakenException();

        // the creator joins automatically; others are invited
        UUID creator = context.getUserId();
        boolean creatorAdded = false;
        Set<RoomUser> users = new HashSet<>();
        for (RoomUser user : room.getUsers()) {
            boolean isCreator = creator.equals(user.getUuid());
            creatorAdded = creatorAdded || isCreator;
            users.add(user.toBuilder()
                    .active(isCreator)
                    .build());
        }

        // always add the creator to the room
        if (!creatorAdded)
            users.add(RoomUser.builder()
                    .uuid(creator)
                    .active(true)
                    .build());

        room = room.toBuilder()
                .clearUsers().users(users)
                .build();

        return dao.create(creator, room);
    }

    public List<Room> read(RoomParams params) {
        return dao.findBy(context.getUserId(), params);
    }

    public Room read(String name) {
        return dao.find(context.getUserId(), name);
    }
}
