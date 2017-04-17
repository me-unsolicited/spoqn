package com.spoqn.server.data;

import lombok.Value;
import lombok.experimental.NonFinal;

@Value
@NonFinal
public class CodedError {
    String code;
    String description;
}
