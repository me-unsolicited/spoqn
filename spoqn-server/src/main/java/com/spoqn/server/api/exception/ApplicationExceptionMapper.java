package com.spoqn.server.api.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spoqn.server.data.entities.CodedError;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {

        ErrorCode code = ErrorCode.from(exception.getMessage());
        String description = code == ErrorCode.UNKNOWN ? exception.getMessage() : code.description();
        CodedError error = new CodedError(code.name(), description);

        return Response.fromResponse(exception.getResponse()).entity(error).build();
    }
}
