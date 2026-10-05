package de.muenchen.oss.refarch.backend.common.validator;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.ENDEZEIT_NOT_AFTER_ANFANGSZEIT;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = TimeRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimeRange {
    String message() default ENDEZEIT_NOT_AFTER_ANFANGSZEIT;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startField() default "beginnZeit";

    String endField() default "endeZeit";
}
