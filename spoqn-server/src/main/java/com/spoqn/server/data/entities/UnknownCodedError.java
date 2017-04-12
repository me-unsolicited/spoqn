package com.spoqn.server.data.entities;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnknownCodedError extends CodedError {

    public static final String CODE = "UNKNOWN";

    private final UUID incident;

    public UnknownCodedError(UUID incident) {
        super(CODE);
        this.incident = incident;
    }
}
