package com.spoqn.server.core;

import javax.inject.Inject;

public class SpoqnContext {

    @Inject PrincipalProvider principal;

    public String getLoginId() {
        return principal.get().getName();
    }
}
