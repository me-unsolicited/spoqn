package com.spoqn.server.api.exception;

import java.util.UUID;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spoqn.server.core.exceptions.SpoqnException;
import com.spoqn.server.data.entities.CodedError;
import com.spoqn.server.data.entities.UnknownCodedError;

@Provider
public class SpoqnExceptionMapper implements ExceptionMapper<SpoqnException> {

    @Override
    public Response toResponse(SpoqnException exception) {

        UUID incident = UUID.randomUUID();
        // TODO log stack trace with incident id

        CodedError error = new UnknownCodedError(incident);
        return Response.serverError().entity(error).build();
    }
}
