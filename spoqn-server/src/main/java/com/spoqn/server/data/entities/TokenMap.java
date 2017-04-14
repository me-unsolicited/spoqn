package com.spoqn.server.data.entities;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="refresh")
@Builder(toBuilder = true)
public class TokenMap {
    private final String access;
    private final String refresh;
}
