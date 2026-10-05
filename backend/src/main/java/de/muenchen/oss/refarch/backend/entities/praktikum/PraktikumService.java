package de.muenchen.oss.refarch.backend.entities.praktikum;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.PRAKTIKUM_ALREADY_EXISTS;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.PRAKTIKUM_NOT_FOUND;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDENT_NOT_FOUND;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ConflictException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.FullPraktikumAggregat;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import de.muenchen.oss.refarch.backend.entities.student.StudentRepository;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.Taetigkeitenblock;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.TaetigkeitenblockRepository;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.Zeitgutschrift;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.ZeitgutschriftRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PraktikumService {

    private final PraktikumRepository repository;

    private final StudentRepository studentRepository;

    private final TaetigkeitenblockRepository taetigkeitenblockRepository;

    private final ZeitgutschriftRepository zeitgutschriftRepository;

    public FullPraktikumAggregat getPraktikum(final int studentenId) {
        final List<Taetigkeitenblock> taetigkeitenblockList = taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentenId);
        final List<Zeitgutschrift> zeitgutschriftList = zeitgutschriftRepository.findByPraktikumStudentId(studentenId);
        final Praktikum p = repository.findById(studentenId).orElseThrow(() -> new NotFoundException(String.format(PRAKTIKUM_NOT_FOUND, studentenId)));
        return new FullPraktikumAggregat(p, taetigkeitenblockList, zeitgutschriftList);
    }

    private Praktikum getPraktikumInternal(final int studentId) {
        return repository.findById(studentId).orElseThrow(() -> new NotFoundException(String.format(PRAKTIKUM_NOT_FOUND, studentId)));
    }

    public Integer createPraktikum(final Praktikum praktikum) {
        final Student s = praktikum.getStudent();
        if (s.getPraktikum() != null) {
            throw new ConflictException(String.format(PRAKTIKUM_ALREADY_EXISTS, s.getStudentId()));
        }
        s.setPraktikum(praktikum);
        studentRepository.save(s);
        repository.save(praktikum);
        return praktikum.getStudentId();
    }

    public void updatePraktikum(final int studentId, final Praktikum praktikum) {
        final Praktikum p = getPraktikumInternal(studentId);
        final Student s = p.getStudent();
        praktikum.setStudent(s);
        praktikum.setStudentId(s.getStudentId());
        s.setPraktikum(praktikum);
        studentRepository.save(s);
        repository.save(praktikum);
    }

    @Transactional
    public void deletePraktikum(final int studentId) {
        final List<Zeitgutschrift> zeitgutschriften = zeitgutschriftRepository.findByPraktikumStudentId(studentId);
        final List<Taetigkeitenblock> taetigkeitenbloecke = taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentId);
        zeitgutschriftRepository.deleteAll(zeitgutschriften);
        taetigkeitenblockRepository.deleteAll(taetigkeitenbloecke);
        final Praktikum praktikum = repository.findById(studentId).orElseThrow(() -> new NotFoundException(String.format(PRAKTIKUM_NOT_FOUND, studentId)));
        final Student s = studentRepository.findById(studentId).orElseThrow(() -> new NotFoundException(String.format(STUDENT_NOT_FOUND, studentId)));
        s.setPraktikum(null);
        studentRepository.save(s);
        repository.delete(praktikum);
    }
}
