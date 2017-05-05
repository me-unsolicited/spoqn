package com.spoqn.server.api.json;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class InstantAdapter extends TemporalAdapter<Instant> {

    @Override
    protected DateTimeFormatter formatter() {
        return DateTimeFormatter.ISO_INSTANT;
    }

    @Override
    protected Instant from(TemporalAccessor temporal) {
        return Instant.from(temporal);
    }
}
