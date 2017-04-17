package com.spoqn.server.api.exception;

import java.text.MessageFormat;
import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import com.spoqn.server.data.CodedError;
import com.spoqn.server.data.UnknownCodedError;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class IncidentExceptionMapper<E extends Throwable> implements ExceptionMapper<E> {

    private static final String MSG_PATTERN = "Incident ID [{0}]";

    @Override
    public Response toResponse(E exception) {

        // log an incident ID with a stack trace
        UUID incident = UUID.randomUUID();
        log.error(MessageFormat.format(MSG_PATTERN, incident), exception);

        // return the incident ID as part of an unknown error response
        CodedError error = new UnknownCodedError(incident);
        return Response.serverError().entity(error).build();
    }
}
