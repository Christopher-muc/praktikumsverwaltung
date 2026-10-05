package de.muenchen.oss.refarch.backend.entities.praktikum.dto;

import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.dto.TaetigkeitenblockDTO;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto.SimpleZeitgutschriftDTO;

import java.time.LocalDate;
import java.util.List;

public record FullPraktikumDTO(
        LocalDate beginnDatum,
        LocalDate endeDatum,
        int studentId,
        int wochenarbeitszeit,
        int benoetigteWochen, List<TaetigkeitenblockDTO> taetigkeiten, List<SimpleZeitgutschriftDTO> zeitgutschriften) {
    @Override
    public List<TaetigkeitenblockDTO> taetigkeiten() {
        return taetigkeiten == null ? null : List.copyOf(taetigkeiten);
    }

    @Override
    public List<SimpleZeitgutschriftDTO> zeitgutschriften() {
        return zeitgutschriften == null ? null : List.copyOf(zeitgutschriften);
    }
}
