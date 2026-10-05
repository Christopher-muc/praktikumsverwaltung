package de.muenchen.oss.refarch.backend.entities.studiengang.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record StudiengangCreationDTO(@NotBlank(message = "Der Name des Studiengangs darf nicht leer sein") @Length(
        min = 1, max = 255, message = "Der Name des Studiengangs muss zwischen 1 und 255 Zeichen haben"
) String name) {
}
