package com.spoqn.server.data.entities;

import java.util.UUID;

import com.spoqn.server.api.exception.ErrorCode;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UnknownCodedError extends CodedError {

    UUID incident;

    public UnknownCodedError(UUID incident) {
        super(ErrorCode.UNKNOWN.name(), ErrorCode.UNKNOWN.description());
        this.incident = incident;
    }
}
