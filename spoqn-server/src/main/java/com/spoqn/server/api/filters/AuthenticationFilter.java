package com.spoqn.server.api.filters;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import com.spoqn.server.core.Logins;
import com.spoqn.server.core.exceptions.AuthenticationException;

import lombok.Data;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTH_SCHEME = "Bearer";
    private static final String AUTH_PREFIX = "Bearer ";

    private static final String CHALLENGE_NO_AUTH = "Bearer";
    private static final String CHALLENGE_BAD_AUTH = "Bearer error=\"invalid_token\"";

    private static final Set<RequestPattern> EXCLUSIONS;
    static {
        Set<RequestPattern> exclusions = new HashSet<>();
        exclusions.add(new RequestPattern("GET", "token"));
        exclusions.add(new RequestPattern("POST", "users"));
        EXCLUSIONS = Collections.unmodifiableSet(exclusions);
    }

    @Inject private Logins logins;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String method = requestContext.getMethod();
        String path = requestContext.getUriInfo().getPath();
        if (EXCLUSIONS.contains(new RequestPattern(method, path)))
            return;

        String auth = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith(AUTH_PREFIX))
            throw new NotAuthorizedException("NO_AUTH_HEADER", CHALLENGE_NO_AUTH);

        String token = auth.substring(AUTH_PREFIX.length());
        String username;
        try {
            username = logins.resolveUsername(token);
        } catch (AuthenticationException e) {
            throw new NotAuthorizedException("BAD_TOKEN", CHALLENGE_BAD_AUTH);
        }

        SecurityContext baseSc = requestContext.getSecurityContext();
        SecurityContext sc = new UserSecurityContext(baseSc, username);
        requestContext.setSecurityContext(sc);
    }

    @Data
    public static class RequestPattern {
        private final String method;
        private final String path;
    }

    private static class UserSecurityContext implements SecurityContext {

        private final SecurityContext base;
        private final Principal principal;

        public UserSecurityContext(SecurityContext base, String user) {
            this.base = base;
            this.principal = new Principal() {
                @Override
                public String getName() {
                    return user;
                }
            };
        }

        @Override
        public Principal getUserPrincipal() {
            return principal;
        }

        @Override
        public boolean isUserInRole(String role) {
            return false;
        }

        @Override
        public boolean isSecure() {
            return base.isSecure();
        }

        @Override
        public String getAuthenticationScheme() {
            return AUTH_SCHEME;
        }
    }
}
