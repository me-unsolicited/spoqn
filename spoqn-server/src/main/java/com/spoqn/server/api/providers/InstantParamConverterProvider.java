package com.spoqn.server.api.providers;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.ext.Provider;

@Provider
public class InstantParamConverterProvider extends TypedParamConverterProvider<Instant> {

    @Override
    protected Class<Instant> getType() {
        return Instant.class;
    }

    @Override
    protected Instant parse(String value) {
        return Instant.from(DateTimeFormatter.ISO_INSTANT.parse(value));
    }

    @Override
    protected String format(Instant value) {
        return DateTimeFormatter.ISO_INSTANT.format(value);
    }
}
