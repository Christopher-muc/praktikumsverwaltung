package de.muenchen.oss.refarch.backend.common.exceptionhandling;

import java.util.List;
import java.util.Map;

public record ErrorResponse(Map<String, List<String>> errors, List<String> globalErrors) {
    public ErrorResponse {
        errors = errors == null ? Map.of() : Map.copyOf(errors);
        globalErrors = globalErrors == null ? List.of() : List.copyOf(globalErrors);
    }
}
