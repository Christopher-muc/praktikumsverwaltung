package de.muenchen.oss.refarch.backend.entities.studium;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDENT_NOT_FOUND;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDIENGANG_ALREADY_EXISTS;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDIENGANG_NOT_FOUND;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDIENGANG_NOT_FOUND_WITH_STUDENT;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.BadInputException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import de.muenchen.oss.refarch.backend.entities.student.StudentRepository;
import de.muenchen.oss.refarch.backend.entities.studiengang.Studiengang;
import de.muenchen.oss.refarch.backend.entities.studiengang.StudiengangRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class StudiumService {

    private final StudentRepository studentRepository;
    private final StudiengangRepository studiengangRepository;

    public void addStudiumToStudent(final StudiumMappingDTO dto) {
        final Student student = studentRepository.findById(dto.studentId()).orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));
        final Studiengang studiengang = studiengangRepository.findById(dto.studiengangId()).orElseThrow(() -> new NotFoundException(STUDENT_NOT_FOUND));

        final List<Studiengang> studiengangList = student.getStudiengaenge();
        for (final Studiengang s : studiengangList) {
            if (s.getStudiengangNr() == studiengang.getStudiengangNr()) {
                throw new BadInputException(String.format(STUDIENGANG_ALREADY_EXISTS, studiengang.getStudiengangNr(), student.getStudentId()));
            }
        }
        studiengangList.add(studiengang);
        student.setStudiengaenge(studiengangList);
        studentRepository.save(student);
    }

    public void removeStudiumFromStudent(final StudiumMappingDTO dto) {
        final Student student = studentRepository.findById(dto.studentId())
                .orElseThrow(() -> new NotFoundException(String.format(STUDENT_NOT_FOUND, dto.studentId())));
        final Studiengang studiengang = studiengangRepository.findById(dto.studiengangId())
                .orElseThrow(() -> new NotFoundException(String.format(STUDIENGANG_NOT_FOUND, dto.studiengangId())));

        final List<Studiengang> studiengangList = student.getStudiengaenge();
        boolean found = false;
        for (final Studiengang s : studiengangList) {
            if (s.getStudiengangNr() == studiengang.getStudiengangNr()) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new BadInputException(String.format(STUDIENGANG_NOT_FOUND_WITH_STUDENT, student.getStudentId(), studiengang.getStudiengangNr()));
        }
        studiengangList.remove(studiengang);
        student.setStudiengaenge(studiengangList);
        studentRepository.save(student);
    }

    public List<Student> getStudentenPerStudiengang(final int id) {
        final Studiengang studiengang = studiengangRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format(STUDIENGANG_NOT_FOUND, id)));
        return studentRepository.findByStudiengaengeContaining(studiengang);
    }
}
