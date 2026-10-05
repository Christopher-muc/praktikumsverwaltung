package de.muenchen.oss.refarch.backend.common.exceptionhandling;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.CHECK_FORMAT;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.BadInputException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ConflictException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationException(final MethodArgumentNotValidException ex) {
        // Feldfehler sammeln
        final Map<String, List<String>> errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())));

        // Globale Fehler sammeln
        final List<String> globalErrors = ex.getBindingResult().getGlobalErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .toList();

        final ErrorResponse errorResponse = new ErrorResponse(errors, globalErrors);
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleNotFoundException(final NotFoundException ex) {
        final ErrorResponse errorResponse = new ErrorResponse(null, List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleConflictException(final ConflictException ex) {
        final ErrorResponse errorResponse = new ErrorResponse(null, List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }

    @ExceptionHandler(BadInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleBadInputException(final BadInputException ex) {
        final ErrorResponse errorResponse = new ErrorResponse(null, List.of(ex.getMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleNotDeserializableException(final HttpMessageNotReadableException ex) {
        final ErrorResponse errorResponse = new ErrorResponse(null, List.of(CHECK_FORMAT));
        log.warn(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
