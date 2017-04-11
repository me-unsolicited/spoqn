package com.spoqn.server.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/messages")
public class MessageResource {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Message getMessages() {
		Message msg = new Message();
		msg.text = "Hello, World!";
		return msg;
	}

	public class Message {
		public String text;
	}
}
