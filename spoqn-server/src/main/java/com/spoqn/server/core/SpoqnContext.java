package com.spoqn.server.core;

import java.util.UUID;

import javax.inject.Inject;

public class SpoqnContext {

    @Inject PrincipalProvider principal;

    public UUID getUserId() {
        return UUID.fromString(principal.get().getName());
    }
}
