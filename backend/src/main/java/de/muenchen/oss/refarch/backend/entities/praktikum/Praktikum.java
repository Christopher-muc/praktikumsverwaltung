package de.muenchen.oss.refarch.backend.entities.praktikum;

import de.muenchen.oss.refarch.backend.common.BaseEntityCustomKey;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Praktikum extends BaseEntityCustomKey {

    @Id
    private int studentId;

    private static final long serialVersionUID = 1L;

    @Column(name = "beginn_datum")
    @NotNull private LocalDate beginnDatum;

    @Column(name = "ende_datum")
    @NotNull private LocalDate endeDatum;

    @MapsId("studentId")
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "wochenarbeitszeit")
    @NotNull private int wochenarbeitszeit;

    @Column(name = "benoetigte_wochen")
    @NotNull private int benoetigteWochen;

}
