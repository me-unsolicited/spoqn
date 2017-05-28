package com.spoqn.server.api;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.spoqn.server.core.services.MessageService;
import com.spoqn.server.data.Message;
import com.spoqn.server.data.params.MessageParams;

@Path("/messages")
public class MessageApi {

    @Inject private MessageService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> get(
            @QueryParam("rooms") Set<String> rooms,
            @QueryParam("users") Set<UUID> userIds,
            @QueryParam("direct") Boolean direct,
            @QueryParam("since") Instant since,
            @QueryParam("until") Instant until,
            @QueryParam("top") Integer top) {

        MessageParams params = MessageParams.builder()
                .rooms(rooms)
                .userIds(userIds)
                .direct(direct)
                .since(since)
                .until(until)
                .top(top)
                .build();

        return service.read(params);
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message get(@PathParam("id") UUID id) {
        return service.read(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message post(Message message) {
        return service.create(message);
    }
}
