package com.spoqn.server.api.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.common.base.Joiner;

import jersey.repackaged.com.google.common.base.Splitter;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

@Value
@Builder
public class ErrorCodes {

    private static final char DELIM = ',';
    private static final Joiner JOINER = Joiner.on(DELIM);
    private static final Splitter SPLITTER = Splitter.on(DELIM);

    @Singular List<ErrorCode> codes;

    public String names() {

        List<String> names = new ArrayList<>(codes.size());
        for (ErrorCode code : codes)
            names.add(code.name());

        return JOINER.join(names);
    }

    public static Optional<ErrorCodes> from(String names) {

        Iterable<String> codeNames = SPLITTER.split(names);

        ErrorCodesBuilder builder = ErrorCodes.builder();
        for (String codeName : codeNames) {
            ErrorCode code = ErrorCode.from(codeName);
            if (code == ErrorCode.UNKNOWN)
                return Optional.empty();

            builder.code(code);
        }

        return Optional.of(builder.build());
    }
}
