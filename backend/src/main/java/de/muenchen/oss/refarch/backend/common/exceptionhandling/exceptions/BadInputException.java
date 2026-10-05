package de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/** Exception if data cannot be found. */
@SuppressWarnings("PMD.MissingSerialVersionUID")
public class BadInputException extends ResponseStatusException {
    /**
     * NotFoundException constructor
     *
     * @param message Exception message
     */
    public BadInputException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
