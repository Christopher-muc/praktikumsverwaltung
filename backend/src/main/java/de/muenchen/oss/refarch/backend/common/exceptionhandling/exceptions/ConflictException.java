package de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception if data cannot be found. */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class ConflictException extends ResponseStatusException {
    public ConflictException(final String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
