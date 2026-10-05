package de.muenchen.oss.refarch.backend.common.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;

public class MinutePrecisionValidator implements ConstraintValidator<MinutePrecision, LocalTime> {

    @Override
    public boolean isValid(final LocalTime value, final ConstraintValidatorContext context) {
        return value == null || value.getSecond() == 0 && value.getNano() == 0;
    }
}
