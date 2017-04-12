package com.spoqn.server.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spoqn.server.core.exceptions.SpoqnException;
import com.spoqn.server.data.entities.CodedError;

@Provider
public class SpoqnExceptionHandler implements ExceptionMapper<SpoqnException> {

    private static final String ERROR_CODE = "UNKNOWN";

    @Override
    public Response toResponse(SpoqnException exception) {
        CodedError error = new CodedError(ERROR_CODE);
        return Response.serverError().entity(error).build();
    }
}
