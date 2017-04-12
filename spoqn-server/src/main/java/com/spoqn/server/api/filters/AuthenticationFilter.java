package com.spoqn.server.api.filters;

import java.io.IOException;
import java.security.Principal;

import javax.annotation.Priority;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

import com.spoqn.server.core.Logins;
import com.spoqn.server.core.exceptions.AuthenticationException;

@Provider
@Priority(Priorities.AUTHENTICATION)
@Component
public class AuthenticationFilter implements ContainerRequestFilter {

    private static final String AUTH_SCHEME = "Bearer";
    private static final String AUTH_PREFIX = "Bearer ";
    private static final String LOGIN_PATH_EXCLUSION = "login";

    private static final String CHALLENGE_NO_AUTH = "Bearer";
    private static final String CHALLENGE_BAD_AUTH = "Bearer error=\"invalid_token\"";

    @Resource
    private Logins logins;

    @Context
    private HttpServletRequest request;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        String path = requestContext.getUriInfo().getPath();
        if (LOGIN_PATH_EXCLUSION.equals(path))
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

    private class UserSecurityContext implements SecurityContext {

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
