package de.muenchen.oss.refarch.backend.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {
    private String startField;
    private String endField;

    @Override
    public void initialize(final ValidDateRange constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            // Hole die Methoden (Getter) für die Start- und Endzeit
            final Method startGetter = value.getClass().getMethod(startField);
            final Method endGetter = value.getClass().getMethod(endField);

            final LocalDate startDate = (LocalDate) startGetter.invoke(value);
            final LocalDate endDate = (LocalDate) endGetter.invoke(value);

            if (startDate == null || endDate == null) {
                return false;
            }

            return endDate.isAfter(startDate);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }
}
