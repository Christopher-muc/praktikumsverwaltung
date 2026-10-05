package de.muenchen.oss.refarch.backend.entities.praktikum.dto;

import de.muenchen.oss.refarch.backend.common.validator.ValidDateRange;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;

@ValidDateRange
public record PraktikumUpdateDTO(LocalDate beginnDatum, LocalDate endeDatum,
        @Min(value = 0, message = "Wochenarbeitszeit muss mindestens 0 Stunden sein") @Max(
                value = 48, message = "Wochenarbeitszeit darf höchstens 48 Stunden sein"
        ) int wochenarbeitszeit,
        @Min(value = 1, message = "Benötigte Wochen müssen mindestens 1 Wochen sein") int benoetigteWochen) {
}
