package de.muenchen.oss.refarch.backend.entities.studiengang;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDIENGANG_NOT_EMPTY;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDIENGANG_NOT_FOUND;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ConflictException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import de.muenchen.oss.refarch.backend.entities.student.StudentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudiengangService {

    protected final StudiengangRepository repository;
    protected final StudentRepository studentRepository;

    public List<Studiengang> getStudiengaenge() {
        return repository.findAll();
    }

    public void deleteStudiengang(final int id) {
        final Studiengang studiengang = repository.findById(id).orElseThrow(() -> new NotFoundException(String.format(STUDIENGANG_NOT_FOUND, id)));
        final List<Student> studentenWithStudiengang = studentRepository.findByStudiengaengeContaining(studiengang);
        if (!studentenWithStudiengang.isEmpty()) {
            throw new ConflictException(String.format(STUDIENGANG_NOT_EMPTY, id));
        }
        repository.delete(studiengang);
    }

    public int createStudiengang(final Studiengang studiengang) {
        final Studiengang s = repository.save(studiengang);
        return s.getStudiengangNr();
    }
}
