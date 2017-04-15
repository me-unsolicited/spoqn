package com.spoqn.server.data.entities;

import lombok.Builder;
import lombok.ToString;
import lombok.Value;

@Value
@ToString(exclude="refresh")
@Builder(toBuilder = true)
public class TokenMap {
    String access;
    String refresh;
}
