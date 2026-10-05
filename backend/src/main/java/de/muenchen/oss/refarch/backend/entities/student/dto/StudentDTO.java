package de.muenchen.oss.refarch.backend.entities.student.dto;

import de.muenchen.oss.refarch.backend.entities.praktikum.dto.PraktikumDTO;
import de.muenchen.oss.refarch.backend.entities.studiengang.Studiengang;
import java.util.List;
import lombok.Data;

@Data
public class StudentDTO {
    private int studentId;
    private String vorname;
    private String nachname;
    private List<Studiengang> studiengaenge;
    private PraktikumDTO praktikum;
    private String url;

    public StudentDTO(final int studentId, final String vorname, final String nachname, final List<Studiengang> studiengaenge,
            final PraktikumDTO praktikum) {
        this.studentId = studentId;
        this.vorname = vorname;
        this.nachname = nachname;
        this.studiengaenge = studiengaenge;
        this.praktikum = praktikum;
        url = "/praktikum?studentenId=" + studentId;
    }
}
