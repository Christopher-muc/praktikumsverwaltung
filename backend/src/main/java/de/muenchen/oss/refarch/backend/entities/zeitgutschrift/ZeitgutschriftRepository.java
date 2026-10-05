package de.muenchen.oss.refarch.backend.entities.zeitgutschrift;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ZeitgutschriftRepository extends JpaRepository<Zeitgutschrift, Integer> {
    List<Zeitgutschrift> findByPraktikumStudentId(int praktikumStudentId);
}
