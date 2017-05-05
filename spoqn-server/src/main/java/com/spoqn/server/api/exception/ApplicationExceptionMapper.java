package com.spoqn.server.api.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.spoqn.server.data.CodedError;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Override
    public Response toResponse(WebApplicationException exception) {

        List<CodedError> errors = new ArrayList<>();

        Optional<ErrorCodes> codes = ErrorCodes.from(exception.getMessage());
        if (codes.isPresent())
            for (ErrorCode code : codes.get().getCodes())
                errors.add(new CodedError(code.name(), code.description()));
        else
            errors.add(new CodedError(ErrorCode.UNKNOWN.name(), exception.getMessage()));

        return Response.fromResponse(exception.getResponse()).entity(errors).build();
    }
}
