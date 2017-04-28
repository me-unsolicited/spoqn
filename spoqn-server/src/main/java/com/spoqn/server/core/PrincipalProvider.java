package com.spoqn.server.core;

import java.security.Principal;

public interface PrincipalProvider {
    Principal get();
}
