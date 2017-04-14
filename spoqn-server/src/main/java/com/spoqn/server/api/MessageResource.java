package com.spoqn.server.api;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import org.springframework.stereotype.Component;

import com.spoqn.server.core.Messages;
import com.spoqn.server.data.entities.Message;

@Component
@Path("/messages")
public class MessageResource {

    @Resource private Messages messages;
    @Context private SecurityContext sc;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> get() {
        return messages.read();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Message get(@PathParam("id") UUID id) {
        return messages.read(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Message post(Message message) {
        return messages.create(sc.getUserPrincipal(), message);
    }
}
