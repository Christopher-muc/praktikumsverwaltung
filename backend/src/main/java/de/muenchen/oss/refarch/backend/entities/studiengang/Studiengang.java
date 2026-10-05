package de.muenchen.oss.refarch.backend.entities.studiengang;

import de.muenchen.oss.refarch.backend.common.BaseEntityCustomKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Studiengang extends BaseEntityCustomKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studiengangNr;

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 255)
    @NotNull @Size(min = 1, max = 255) private String name;

}
