package com.spoqn.server.core.exceptions;

public class AuthenticationException extends RuntimeException {
    private static final long serialVersionUID = 5365837251068238632L;

    public AuthenticationException() {
        super();
    }

    public AuthenticationException(String message) {
        super(message);
    }

    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}
