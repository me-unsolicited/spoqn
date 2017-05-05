package com.spoqn.server.data.mappers;

public final class MapperHelper {

    public static String pkg() {
        return MapperHelper.class.getPackage().getName();
    }

    private MapperHelper() {
        throw new IllegalAccessError("Utility class");
    }
}
