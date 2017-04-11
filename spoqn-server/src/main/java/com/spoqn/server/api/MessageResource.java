package com.spoqn.server.api;

import java.util.List;

import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.spoqn.server.core.Messages;
import com.spoqn.server.data.entities.Message;

@Component
@Path("/messages")
public class MessageResource {

    @Resource
    private Messages messages;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Message> get() {
        return messages.getAll();
    }
}
