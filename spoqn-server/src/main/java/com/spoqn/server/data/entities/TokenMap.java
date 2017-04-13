package com.spoqn.server.data.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="refresh")
public class TokenMap {
    private final String access;
    private final String refresh;
}
