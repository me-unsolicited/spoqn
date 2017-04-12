package com.spoqn.server.api.exception;

import java.text.MessageFormat;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spoqn.server.data.entities.CodedError;
import com.spoqn.server.data.entities.UnknownCodedError;

import lombok.extern.slf4j.Slf4j;

@Provider
@Slf4j
public class ThrowableMapper implements ExceptionMapper<Throwable> {

    private static final String MSG_PATTERN = "Incident ID [{0}]";

    @Override
    public Response toResponse(Throwable exception) {

        // log an incident ID with a stack trace
        UUID incident = UUID.randomUUID();
        log.error(MessageFormat.format(MSG_PATTERN, incident), exception);

        // return the incident ID as part of an unknown error response
        CodedError error = new UnknownCodedError(incident);
        return Response.serverError().entity(error).build();
    }
}
