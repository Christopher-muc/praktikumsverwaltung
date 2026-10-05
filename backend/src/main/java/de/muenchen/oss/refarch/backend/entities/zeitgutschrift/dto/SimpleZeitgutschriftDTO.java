package de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto;

import java.time.LocalDate;

public record SimpleZeitgutschriftDTO(int id, LocalDate tag, int mengeMinuten, String grund) {
}
