package de.muenchen.oss.refarch.backend.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalTime;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, Object> {
    private String startField;
    private String endField;

    @Override
    public void initialize(final ValidTimeRange constraintAnnotation) {
        this.startField = constraintAnnotation.startField();
        this.endField = constraintAnnotation.endField();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        try {
            // Hole die Methoden (Getter) für die Start- und Endzeit
            final Method startGetter = value.getClass().getMethod(startField);
            final Method endGetter = value.getClass().getMethod(endField);

            final LocalTime startTime = (LocalTime) startGetter.invoke(value);
            final LocalTime endTime = (LocalTime) endGetter.invoke(value);

            if (startTime == null || endTime == null) {
                return false;
            }

            return endTime.isAfter(startTime);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            return false;
        }
    }
}
