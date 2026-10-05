package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaetigkeitenblockRepository extends JpaRepository<Taetigkeitenblock, TaetigkeitenblockID> {
    List<Taetigkeitenblock> findByTaetigkeitenblockIDStudentId(int taetigkeitenblockIDStudentId);

    List<Taetigkeitenblock> findByTaetigkeitenblockIDTagAndTaetigkeitenblockIDStudentId(LocalDate date, int studentid);
}
