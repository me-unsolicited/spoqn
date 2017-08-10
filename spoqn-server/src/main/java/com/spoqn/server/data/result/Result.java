package com.spoqn.server.data.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Result<T> {

    T get();

    public static <T> T get(Result<T> result) {

        return result == null ? null : result.get();
    }

    public static <T> List<T> get(List<Result<T>> results) {

        List<T> values = new ArrayList<>(results.size());
        for (Result<T> result : results)
            values.add(result.get());

        return Collections.unmodifiableList(values);
    }

    public static <T> Set<T> get(Set<Result<T>> results) {

        Set<T> values = new HashSet<>();
        for (Result<T> result : results)
            values.add(result.get());

        return Collections.unmodifiableSet(values);
    }
}
