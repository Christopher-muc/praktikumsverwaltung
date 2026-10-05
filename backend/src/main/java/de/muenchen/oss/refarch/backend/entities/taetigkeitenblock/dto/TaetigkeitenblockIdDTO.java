package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.dto;

import de.muenchen.oss.refarch.backend.common.validator.MinutePrecision;
import de.muenchen.oss.refarch.backend.common.validator.ValidTimeRange;
import java.time.LocalDate;
import java.time.LocalTime;

@ValidTimeRange()
public record TaetigkeitenblockIdDTO(
        int studentId,

        @MinutePrecision LocalTime beginnZeit,

        @MinutePrecision LocalTime endeZeit,

        LocalDate tag) {
}
