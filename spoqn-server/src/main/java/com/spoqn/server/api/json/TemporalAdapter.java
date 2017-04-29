package com.spoqn.server.api.json;

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public abstract class TemporalAdapter<T extends TemporalAccessor> implements JsonSerializer<T>, JsonDeserializer<T> {

    @Override
    public JsonElement serialize(T src, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(formatter().format(src));
    }

    @Override
    public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        String formattedTemporal = json.getAsString();
        TemporalAccessor temporal = formatter().parse(formattedTemporal);

        return from(temporal);
    }

    protected abstract DateTimeFormatter formatter();
    protected abstract T from(TemporalAccessor temporal);
}
