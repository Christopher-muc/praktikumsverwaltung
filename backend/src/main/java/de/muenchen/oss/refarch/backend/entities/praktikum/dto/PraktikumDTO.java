package de.muenchen.oss.refarch.backend.entities.praktikum.dto;

import de.muenchen.oss.refarch.backend.common.validator.ValidDateRange;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

@ValidDateRange
public record PraktikumDTO(
        LocalDate beginnDatum,
        LocalDate endeDatum,
        @Min(value = 1, message = "StudentID muss positiv sein") int studentId,
        @Min(value = 0, message = "Wochenarbeitszeit muss mindestens 0 h betragen") @Max(
                value = 48, message = "Wochenarbeitszeit darf höchstens 48 h sein"
        ) int wochenarbeitszeit,
        @Min(value = 1, message = "benötigte Wochen muss mindestens 1 betragen") int benoetigteWochen) {
}
