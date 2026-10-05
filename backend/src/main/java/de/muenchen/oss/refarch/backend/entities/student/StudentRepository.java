package de.muenchen.oss.refarch.backend.entities.student;

import de.muenchen.oss.refarch.backend.entities.studiengang.Studiengang;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    List<Student> findByNachnameContainingIgnoreCaseOrVornameContainingIgnoreCase(String nachname, String vorname);

    List<Student> findByNachnameContainingIgnoreCase(String nachname);

    List<Student> findByVornameContainingIgnoreCase(String vorname);

    List<Student> findByStudiengaengeContaining(Studiengang studiengaenge);
}
