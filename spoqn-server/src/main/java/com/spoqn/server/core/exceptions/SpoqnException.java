package com.spoqn.server.core.exceptions;

public class SpoqnException extends RuntimeException {
    private static final long serialVersionUID = 9218622168549228866L;

    public SpoqnException() {
        super();
    }

    public SpoqnException(String message) {
        super(message);
    }

    public SpoqnException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpoqnException(Throwable cause) {
        super(cause);
    }
}
