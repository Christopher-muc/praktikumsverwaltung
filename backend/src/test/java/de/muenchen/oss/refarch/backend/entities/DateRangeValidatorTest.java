package de.muenchen.oss.refarch.backend.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import de.muenchen.oss.refarch.backend.common.validator.DateRangeValidator;
import de.muenchen.oss.refarch.backend.common.validator.ValidDateRange;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class DateRangeValidatorTest {

    // Einfache Hilfsklasse mit passenden Gettern
    public static class Dummy {
        private final LocalDate beginnDatum;
        private final LocalDate endeDatum;

        Dummy(LocalDate beginnDatum, LocalDate endeDatum) {
            this.beginnDatum = beginnDatum;
            this.endeDatum = endeDatum;
        }

        public LocalDate getBeginnDatum() {
            return beginnDatum;
        }

        public LocalDate getEndeDatum() {
            return endeDatum;
        }
    }

    @Test
    void givenStartDateNull_thenIsValidReturnsFalse() {
        DateRangeValidator validator = new DateRangeValidator();
        ValidDateRange annotation = mock(ValidDateRange.class);
        when(annotation.startField()).thenReturn("getBeginnDatum");
        when(annotation.endField()).thenReturn("getEndeDatum");
        validator.initialize(annotation);

        Dummy dummy = new Dummy(null, LocalDate.of(2024, 2, 1));

        assertFalse(validator.isValid(dummy, null));
    }

    @Test
    void givenEndDateNull_thenIsValidReturnsFalse() {
        DateRangeValidator validator = new DateRangeValidator();
        ValidDateRange annotation = mock(ValidDateRange.class);
        when(annotation.startField()).thenReturn("getBeginnDatum");
        when(annotation.endField()).thenReturn("getEndeDatum");
        validator.initialize(annotation);

        Dummy dummy = new Dummy(LocalDate.of(2024, 1, 1), null);

        assertFalse(validator.isValid(dummy, null));
    }

    @Test
    void givenEndDateAfterStartDate_thenIsValidReturnsTrue() {
        DateRangeValidator validator = new DateRangeValidator();
        ValidDateRange annotation = mock(ValidDateRange.class);
        when(annotation.startField()).thenReturn("getBeginnDatum");
        when(annotation.endField()).thenReturn("getEndeDatum");
        validator.initialize(annotation);

        Dummy dummy = new Dummy(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 2, 1));

        assertTrue(validator.isValid(dummy, null));
    }

    @Test
    void givenEndDateEqualsStartDate_thenIsValidReturnsFalse() {
        DateRangeValidator validator = new DateRangeValidator();
        ValidDateRange annotation = mock(ValidDateRange.class);
        when(annotation.startField()).thenReturn("getBeginnDatum");
        when(annotation.endField()).thenReturn("getEndeDatum");
        validator.initialize(annotation);

        Dummy dummy = new Dummy(LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 1));

        assertFalse(validator.isValid(dummy, null));
    }

    @Test
    void givenEndDateBeforeStartDate_thenIsValidReturnsFalse() {
        DateRangeValidator validator = new DateRangeValidator();
        ValidDateRange annotation = mock(ValidDateRange.class);
        when(annotation.startField()).thenReturn("getBeginnDatum");
        when(annotation.endField()).thenReturn("getEndeDatum");
        validator.initialize(annotation);

        Dummy dummy = new Dummy(LocalDate.of(2024, 2, 1), LocalDate.of(2024, 1, 1));

        assertFalse(validator.isValid(dummy, null));
    }
}
