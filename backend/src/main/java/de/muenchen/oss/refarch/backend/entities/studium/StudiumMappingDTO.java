package de.muenchen.oss.refarch.backend.entities.studium;

import jakarta.validation.constraints.Min;

public record StudiumMappingDTO(@Min(value = 1, message = "Die StudentID muss positiv sein.") int studentId,
        @Min(value = 1, message = "Die StudiengangID muss positiv sein.") int studiengangId) {
}
