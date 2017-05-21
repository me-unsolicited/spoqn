package com.spoqn.server.api.exception;

public enum ErrorCode {
    UNKNOWN("Unknown error"),
    NO_AUTH_HEADER("Expected Authorization header"),
    BAD_TOKEN("Access token is invalid or expired"),
    BAD_REFRESH_TOKEN("Refresh token is invalid or expired"),
    BAD_LOGIN("Login credentials are invalid"),
    EXPECTED_BASIC_AUTH("Expected Authorization: Basic"),
    EXPECTED_BASIC_AUTH_BASE64("Expected username:password to be Base64 encoded"),
    MALFORMED_BASIC_AUTH("Expected username:password format"),
    USERNAME_TAKEN("Username is taken"),
    PASSWORD_INADEQUATE("Password does not meet requirements"),
    USER_NOT_FOUND("User cannot be found"),
    MALFORMED_PARAM("Parameter string cannot be parsed");

    public static final ErrorCode from(String code) {

        if (code == null)
            return UNKNOWN;

        try {
            return ErrorCode.valueOf(code);
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }
}
