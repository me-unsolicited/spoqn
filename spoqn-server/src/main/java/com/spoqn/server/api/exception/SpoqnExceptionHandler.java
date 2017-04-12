package com.spoqn.server.api.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spoqn.server.core.exceptions.SpoqnException;
import com.spoqn.server.data.entities.ErrorEntity;

@Provider
public class SpoqnExceptionHandler implements ExceptionMapper<SpoqnException> {

    private static final String ERROR_CODE = "UNKNOWN";

    @Override
    public Response toResponse(SpoqnException exception) {
        ErrorEntity error = new ErrorEntity(ERROR_CODE);
        return Response.serverError().entity(error).build();
    }
}
