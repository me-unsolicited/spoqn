package com.spoqn.server.data;

import java.util.Optional;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.spoqn.server.api.exception.ErrorCode;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnknownCodedError extends CodedError {

    UUID incident;

    public UnknownCodedError(@NotNull UUID incident, String description) {
        super(ErrorCode.UNKNOWN.name(), defaultIfNull(description));
        this.incident = incident;
    }

    private static String defaultIfNull(String description) {
        return Optional.ofNullable(description).orElse(ErrorCode.UNKNOWN.description());
    }
}
