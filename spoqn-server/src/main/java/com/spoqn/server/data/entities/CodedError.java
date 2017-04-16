package com.spoqn.server.data.entities;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class CodedError {
    String code;
    String description;
}
