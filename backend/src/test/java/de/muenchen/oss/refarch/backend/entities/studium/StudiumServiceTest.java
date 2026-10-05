package de.muenchen.oss.refarch.backend.entities.studium;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.BadInputException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import de.muenchen.oss.refarch.backend.entities.student.StudentRepository;
import de.muenchen.oss.refarch.backend.entities.studiengang.Studiengang;
import de.muenchen.oss.refarch.backend.entities.studiengang.StudiengangRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudiumServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudiengangRepository studiengangRepository;

    @InjectMocks
    private StudiumService service;

    private Student student;
    private Studiengang studiengang;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1);
        student.setStudiengaenge(new ArrayList<>());

        studiengang = new Studiengang();
        studiengang.setStudiengangNr(100);
    }

    @Test
    void givenValidStudium_thenAddsStudiengang() {
        final StudiumMappingDTO dto = new StudiumMappingDTO(1, 100);

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        when(studiengangRepository.findById(100))
                .thenReturn(Optional.of(studiengang));

        service.addStudiumToStudent(dto);

        assertEquals(1, student.getStudiengaenge().size());
        assertTrue(student.getStudiengaenge().contains(studiengang));

        verify(studentRepository).save(student);
    }

    @Test
    void givenMissingStudentOnAdd_thenThrowsNotFoundException() {
        final StudiumMappingDTO dto = new StudiumMappingDTO(1, 100);

        when(studentRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.addStudiumToStudent(dto));

        verify(studentRepository, never()).save(student);
    }

    @Test
    void givenMissingStudiengangOnAdd_thenThrowsNotFoundException() {
        final StudiumMappingDTO dto = new StudiumMappingDTO(1, 100);

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        when(studiengangRepository.findById(100))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.addStudiumToStudent(dto));

        verify(studentRepository, never()).save(student);
    }

    @Test
    void givenAlreadyAssignedStudiengang_thenThrowsBadInputException() {
        final StudiumMappingDTO dto = new StudiumMappingDTO(1, 100);

        student.setStudiengaenge(
                new ArrayList<>(List.of(studiengang)));

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        when(studiengangRepository.findById(100))
                .thenReturn(Optional.of(studiengang));

        assertThrows(
                BadInputException.class,
                () -> service.addStudiumToStudent(dto));

        verify(studentRepository, never()).save(student);
    }

    @Test
    void givenAssignedStudiengang_thenRemovesStudiengang() {
        final StudiumMappingDTO dto = new StudiumMappingDTO(1, 100);

        student.setStudiengaenge(
                new ArrayList<>(List.of(studiengang)));

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        when(studiengangRepository.findById(100))
                .thenReturn(Optional.of(studiengang));

        service.removeStudiumFromStudent(dto);

        assertTrue(student.getStudiengaenge().isEmpty());

        verify(studentRepository).save(student);
    }

    @Test
    void givenMissingStudentOnRemove_thenThrowsNotFoundException() {
        final StudiumMappingDTO dto = new StudiumMappingDTO(1, 100);

        when(studentRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.removeStudiumFromStudent(dto));

        verify(studentRepository, never()).save(student);
    }

    @Test
    void givenMissingStudiengangOnRemove_thenThrowsNotFoundException() {
        final StudiumMappingDTO dto = new StudiumMappingDTO(1, 100);

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        when(studiengangRepository.findById(100))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.removeStudiumFromStudent(dto));

        verify(studentRepository, never()).save(student);
    }

    @Test
    void givenUnassignedStudiengang_thenThrowsBadInputException() {
        final StudiumMappingDTO dto = new StudiumMappingDTO(1, 100);

        when(studentRepository.findById(1))
                .thenReturn(Optional.of(student));

        when(studiengangRepository.findById(100))
                .thenReturn(Optional.of(studiengang));

        assertThrows(
                BadInputException.class,
                () -> service.removeStudiumFromStudent(dto));

        verify(studentRepository, never()).save(student);
    }

    @Test
    void givenExistingStudiengang_thenReturnsStudents() {
        final Student student2 = new Student();
        student2.setStudentId(2);

        final List<Student> students = List.of(student, student2);

        when(studiengangRepository.findById(100))
                .thenReturn(Optional.of(studiengang));

        when(studentRepository.findByStudiengaengeContaining(studiengang))
                .thenReturn(students);

        final List<Student> result = service.getStudentenPerStudiengang(100);

        assertEquals(students, result);

        verify(studentRepository)
                .findByStudiengaengeContaining(studiengang);
    }

    @Test
    void givenMissingStudiengangOnGetStudents_thenThrowsNotFoundException() {
        when(studiengangRepository.findById(100))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.getStudentenPerStudiengang(100));
    }
}
