package com.spoqn.server.api.util;

import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.spoqn.server.api.json.annotations.DeserializeNullToEmpty;
import com.spoqn.server.core.exceptions.SpoqnException;

import lombok.Builder;
import lombok.Singular;

/**
 * GSON type adapter which fixes null collections
 * in @{@link DeserializeNullToEmpty} annotated classes, such that they will be
 * initialized to empty collections instead of null.
 * <p>
 * This fixes compatibility issues with @{@link Singular} annotated fields
 * in @{@link Builder} annotated classes.
 * <p>
 * Lombok normally handles this on its own, but GSON goes around Lombok to
 * reflectively initialize objects with null fields.
 * <p>
 * Without this fix, there would be a {@link NullPointerException} on the first
 * invocation of {@code obj.toBuilder()}.
 *
 * @param <T>
 */
public class NullToEmptyAdapter<T> extends TypeAdapter<T> {

    private static final String MSG_TYPE_NOT_SUPPORTED = "Collections of type %s are not supported";

    public static TypeAdapterFactory FACTORY = new TypeAdapterFactory() {

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            return new NullToEmptyAdapter<T>(gson.getDelegateAdapter(this, type));
        }
    };

    private TypeAdapter<T> delegate;

    public NullToEmptyAdapter(TypeAdapter<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(JsonWriter out, T value) throws IOException {
        delegate.write(out, value);
    }

    @Override
    public T read(JsonReader in) throws IOException {
        try {
            return fix(delegate.read(in));
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new SpoqnException("Unable to update null fields to empty collections", e);
        }
    }

    private T fix(T obj) throws IllegalArgumentException, IllegalAccessException {

        Class<? extends Object> cl = obj.getClass();
        if (cl.getAnnotation(DeserializeNullToEmpty.class) == null)
            return obj;

        // gather all Collection-type fields
        Field[] fields = cl.getDeclaredFields();
        List<Field> collections = new ArrayList<>(fields.length);
        for (Field field : fields)
            if (Collection.class.isAssignableFrom(field.getType()))
                collections.add(field);

        // make fields accessible, which are probably private and/or final
        Field.setAccessible(collections.toArray(new AccessibleObject[0]), true);

        // change nulls to empty collections
        for (Field collection : collections)
            if (collection.get(obj) == null)
                collection.set(obj, empty(collection.getType()));

        return obj;
    }

    private Object empty(Class<?> cl) {

        if (List.class.isAssignableFrom(cl))
            return Collections.emptyList();
        else if (Set.class.isAssignableFrom(cl))
            return Collections.emptySet();

        throw new SpoqnException(String.format(MSG_TYPE_NOT_SUPPORTED, cl));
    }
}
