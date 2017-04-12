package com.spoqn.server.api.exception;

import javax.ws.rs.ServerErrorException;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerExceptionMapper extends IncidentExceptionMapper<ServerErrorException> {
}
