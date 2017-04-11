package com.spoqn.server.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.spoqn.server.data.entities.Message;

@Path("/messages")
public class MessageResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Message getMessages() {
        Message msg = new Message();
        msg.setText("Hello, World!");
        return msg;
    }
}
