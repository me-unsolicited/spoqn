package com.spoqn.server.api.json;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class LocalDateAdapter extends TemporalAdapter<LocalDate> {

    @Override
    protected DateTimeFormatter formatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE;
    }

    @Override
    protected LocalDate from(TemporalAccessor temporal) {
        return LocalDate.from(temporal);
    }
}
