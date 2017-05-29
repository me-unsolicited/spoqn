package com.spoqn.server.api.providers;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.ext.ParamConverter;
import javax.ws.rs.ext.ParamConverterProvider;

public abstract class TypedParamConverterProvider<U> implements ParamConverterProvider {

    private class TypedParamConverter<T> implements ParamConverter<T> {

        private final Class<T> cl;

        public TypedParamConverter(Class<T> cl) {
            this.cl = cl;
        }

        @Override
        public T fromString(String value) {
            return value == null ? null : parse(cl, value);
        }

        @Override
        public String toString(T value) {
            return value == null ? null : formatT(value);
        }
    }

    @Override
    public <T> ParamConverter<T> getConverter(Class<T> rawType, Type genericType, Annotation[] annotations) {

        if (getType().isAssignableFrom(rawType) && rawType.isAssignableFrom(getType()))
            return new TypedParamConverter<T>(rawType);
        else
            return null;
    }

    private <T> T parse(Class<T> cl, String value) {
        return cl.cast(parse(value));
    }

    private <T> String formatT(T value) {
        return format(getType().cast(value));
    }

    protected abstract Class<U> getType();

    protected abstract U parse(String value);

    protected abstract String format(U value);
}
