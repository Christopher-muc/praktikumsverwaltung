package de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto;

import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import org.hibernate.validator.constraints.Length;

public record ZeitgutschriftUpdateDTO(LocalDate tag,
        @Min(value = 1, message = "Zeitgutschrift muss ein positiver Wert sein") int mengeMinuten,
        @Length(min = 0, max = 255, message = "Angabe des Grundes muss zwischen 0 und 250 Zeichen enthalten") String grund,
        @Min(value = 0, message = "Praktikum ID muss größer 0 sein") int praktikumID,
        @Min(value = 0, message = "Zeitgutschrift ID muss größer 0 sein") int zeitgutschriftID) {
}
