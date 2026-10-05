package de.muenchen.oss.refarch.backend.common.validator;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.ENDEDATUM_NOT_AFTER_ANFANGSDATUM;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = DateRangeValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateRange {
    String message() default ENDEDATUM_NOT_AFTER_ANFANGSDATUM;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String startField() default "beginnDatum";

    String endField() default "endeDatum";
}
