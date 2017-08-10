package com.spoqn.server.api;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.spoqn.server.api.exception.ErrorCode;
import com.spoqn.server.core.exceptions.RoomNameTakenException;
import com.spoqn.server.core.services.RoomService;
import com.spoqn.server.data.Room;
import com.spoqn.server.data.params.RoomParams;

@Path("/rooms")
public class RoomApi {

    @Inject private RoomService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Room> get(
            @QueryParam("users") Set<UUID> users,
            @QueryParam("topics") Set<UUID> topics,
            @QueryParam("since") Instant since,
            @QueryParam("until") Instant until,
            @QueryParam("top") Integer top) {

        RoomParams params = RoomParams.builder()
                .users(users)
                .topics(topics)
                .since(since)
                .until(until)
                .top(top)
                .build();

        return service.read(params);
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Room get(@PathParam("name") String name) {
        return service.read(name);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Room post(Room room) {

        try {
            return service.create(room);
        } catch (RoomNameTakenException e) {
            throw new BadRequestException(ErrorCode.ROOM_NAME_TAKEN.name(), e);
        }
    }
}
