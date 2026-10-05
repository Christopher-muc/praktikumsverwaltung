package de.muenchen.oss.refarch.backend.entities.zeitgutschrift;

import de.muenchen.oss.refarch.backend.common.BaseEntityCustomKey;
import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
public class Zeitgutschrift extends BaseEntityCustomKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @NotNull private LocalDate tag;

    @Column(nullable = false)
    @NotNull private int mengeMinuten;

    @Column(nullable = false)
    @NotNull @Size(min = 1, max = 255) private String grund;

    @ManyToOne
    private Praktikum praktikum;
}
