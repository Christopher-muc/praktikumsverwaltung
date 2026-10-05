package de.muenchen.oss.refarch.backend.entities.student;

import de.muenchen.oss.refarch.backend.common.BaseEntityCustomKey;
import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.studiengang.Studiengang;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Student extends BaseEntityCustomKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int studentId;

    private static final long serialVersionUID = 1L;

    @Column(nullable = false, length = 255)
    @NotNull @Size(min = 1, max = 255) private String vorname;

    @Column(nullable = false, length = 255)
    @NotNull @Size(min = 1, max = 255) private String nachname;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "studiengang_student", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "studiengang_nr")
    )
    private List<Studiengang> studiengaenge;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private Praktikum praktikum;
}
