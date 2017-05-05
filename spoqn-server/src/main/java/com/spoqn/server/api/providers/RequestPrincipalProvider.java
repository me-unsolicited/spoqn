package com.spoqn.server.api.providers;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.core.SecurityContext;

import com.spoqn.server.core.PrincipalProvider;

public class RequestPrincipalProvider implements PrincipalProvider {

    @Inject Provider<SecurityContext> sc;

    @Override
    public Principal get() {
        return sc.get().getUserPrincipal();
    }
}
