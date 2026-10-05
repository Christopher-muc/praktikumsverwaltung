package de.muenchen.oss.refarch.backend.entities.student;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDENT_NOT_FOUND;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.praktikum.PraktikumRepository;
import de.muenchen.oss.refarch.backend.entities.student.dto.CreateUpdateStudentRequestDTO;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.Taetigkeitenblock;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.TaetigkeitenblockRepository;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.Zeitgutschrift;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.ZeitgutschriftRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository repository;

    private final JdbcTemplate jdbcTemplate;
    private final TaetigkeitenblockRepository taetigkeitenblockRepository;

    private final ZeitgutschriftRepository zeitgutschriftRepository;
    private final PraktikumRepository praktikumRepository;

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public int createStudent(final Student student) {
        repository.save(student);
        return student.getStudentId();
    }

    public Student getStudent(final int id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(String.format(STUDENT_NOT_FOUND, id)));
    }

    @Transactional
    public void deleteStudentAndRelatedData(final int studentId) {
        final Student student = repository.findById(studentId).orElseThrow(() -> new NotFoundException(String.format(STUDENT_NOT_FOUND, studentId)));
        final List<Zeitgutschrift> zeitgutschriften = zeitgutschriftRepository.findByPraktikumStudentId(studentId);
        final List<Taetigkeitenblock> taetigkeitenbloecke = taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentId);
        zeitgutschriftRepository.deleteAll(zeitgutschriften);
        taetigkeitenblockRepository.deleteAll(taetigkeitenbloecke);
        final Optional<Praktikum> praktikum = praktikumRepository.findById(studentId);
        student.setPraktikum(null);
        if (praktikum.isPresent()) {
            praktikumRepository.delete(praktikum.get());
        }
        repository.delete(student);
    }

    public Student updateStudent(final int id, final CreateUpdateStudentRequestDTO body) {
        final Student s = repository.findById(id).orElseThrow(() -> new NotFoundException(String.format(STUDENT_NOT_FOUND, id)));
        s.setVorname(body.getVorname());
        s.setNachname(body.getNachname());
        repository.save(s);
        return s;
    }

    public List<Student> findStudentsByName(final String vorname, final String nachname) {
        if (vorname.isBlank()) {
            return repository.findByNachnameContainingIgnoreCase(nachname);
        } else if (nachname.isBlank()) {
            return repository.findByVornameContainingIgnoreCase(vorname);
        } else {
            return repository.findByNachnameContainingIgnoreCaseOrVornameContainingIgnoreCase(nachname, vorname);
        }
    }
}
