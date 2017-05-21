package com.spoqn.server.api.providers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;
import javax.ws.rs.ext.Provider;

import com.spoqn.server.api.exception.ErrorCode;

@Provider
public class InstantParamConverterProvider implements ParamConverterProvider {

    private class InstantParamConverter<T> implements ParamConverter<T> {

        private final Class<T> rawType;

        public InstantParamConverter(Class<T> rawType) {
            this.rawType = rawType;
        }

        @Override
        public T fromString(String value) {

            if (value == null)
                return null;
            else
                return rawType.cast(parse(value));
        }

        private Instant parse(String value) {

            try {
                return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(value));
            } catch (DateTimeParseException e) {
                throw new BadRequestException(ErrorCode.MALFORMED_PARAM.name(), e);
            }
        }

        @Override
        public String toString(T value) {
            return DateTimeFormatter.ISO_INSTANT.format(Instant.class.cast(value));
        }
    }

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {

        if (Instant.class.isAssignableFrom(rawType))
            return new InstantParamConverter<T>(rawType);
        else
            return null;
    }
}
