package com.spoqn.server.data.entities;

import java.util.UUID;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnknownCodedError extends CodedError {

    public static final String CODE = "UNKNOWN";

    UUID incident;

    public UnknownCodedError(UUID incident) {
        super(CODE);
        this.incident = incident;
    }
}
