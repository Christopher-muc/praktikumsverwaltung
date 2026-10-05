package de.muenchen.oss.refarch.backend.entities;

import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import de.muenchen.oss.refarch.backend.entities.studiengang.Studiengang;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class TestData {

    private Student student1;
    private Student student2;
    private Student student3;

    private Studiengang studiengang1;
    private Studiengang studiengang2;

    private Praktikum praktikum1;
    private Praktikum praktikum2;
    private Praktikum praktikum3;

    public void setUp() {
        // Studiengänge
        studiengang1 = new Studiengang();
        studiengang1.setName("Informatik");
        // Optional: studiengangNr setzen, falls benötigt
        studiengang1.setStudiengangNr(100);

        studiengang2 = new Studiengang();
        studiengang2.setName("Wirtschaftsinformatik");
        studiengang2.setStudiengangNr(102);

        // Studenten
        student1 = new Student();
        student1.setVorname("Max");
        student1.setNachname("Mustermann");
        student1.setStudiengaenge(List.of(studiengang1, studiengang2));

        student2 = new Student();
        student2.setVorname("Anna");
        student2.setNachname("Müller");
        student2.setStudiengaenge(List.of(studiengang1));

        student3 = new Student();
        student3.setVorname("Peter");
        student3.setNachname("Schmidt");
        student3.setStudiengaenge(List.of(studiengang2));

        // Praktika
        praktikum1 = new Praktikum();
        praktikum1.setBeginnDatum(LocalDate.of(2024, 6, 1));
        praktikum1.setEndeDatum(LocalDate.of(2024, 8, 31));
        praktikum1.setWochenarbeitszeit(40);
        praktikum1.setBenoetigteWochen(12);
        praktikum1.setStudent(student1);
        praktikum1.setStudentId(student1.getStudentId()); // falls benötigt

        praktikum2 = new Praktikum();
        praktikum2.setBeginnDatum(LocalDate.of(2024, 7, 1));
        praktikum2.setEndeDatum(LocalDate.of(2024, 9, 30));
        praktikum2.setWochenarbeitszeit(30);
        praktikum2.setBenoetigteWochen(10);
        praktikum2.setStudent(student2);
        praktikum2.setStudentId(student2.getStudentId());

        praktikum3 = new Praktikum();
        praktikum3.setBeginnDatum(LocalDate.of(2024, 5, 1));
        praktikum3.setEndeDatum(LocalDate.of(2024, 7, 31));
        praktikum3.setWochenarbeitszeit(20);
        praktikum3.setBenoetigteWochen(8);
        praktikum3.setStudent(student3);
        praktikum3.setStudentId(student3.getStudentId());

        // Studenten mit Praktikum verknüpfen
        student1.setPraktikum(praktikum1);
        student2.setPraktikum(praktikum2);
        student3.setPraktikum(praktikum3);
    }

}
