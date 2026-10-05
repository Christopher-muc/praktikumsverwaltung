package de.muenchen.oss.refarch.backend.entities.student.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class CreateUpdateStudentRequestDTO {
    @NotBlank(message = "Vorname darf nicht leer sein") @Length(min = 2, max = 255, message = "Vorname muss zwischen 2 und 255 Zeichen lang sein") private String vorname;
    @NotBlank(message = "Vorname darf nicht leer sein") @Length(min = 2, max = 255, message = "Nachname muss zwischen 2 und 255 Zeichen lang sein") private String nachname;
}
