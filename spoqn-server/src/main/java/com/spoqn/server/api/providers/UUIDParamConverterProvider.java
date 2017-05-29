package com.spoqn.server.api.providers;

import java.util.UUID;

import javax.ws.rs.ext.Provider;

@Provider
public class UUIDParamConverterProvider extends TypedParamConverterProvider<UUID> {

    @Override
    protected Class<UUID> getType() {
        return UUID.class;
    }

    @Override
    protected UUID parse(String value) {
        return UUID.fromString(value);
    }

    @Override
    protected String format(UUID value) {
        return value.toString();
    }
}
