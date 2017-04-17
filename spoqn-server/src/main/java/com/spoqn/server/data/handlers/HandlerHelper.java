package com.spoqn.server.data.handlers;

public final class HandlerHelper {

    public static String pkg() {
        return HandlerHelper.class.getPackage().getName();
    }

    private HandlerHelper() {
        throw new IllegalAccessError("Utility class");
    }
}
