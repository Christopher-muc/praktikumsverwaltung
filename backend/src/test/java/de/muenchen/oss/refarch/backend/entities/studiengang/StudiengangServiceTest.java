package de.muenchen.oss.refarch.backend.entities.studiengang;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ConflictException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.TestData;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import de.muenchen.oss.refarch.backend.entities.student.StudentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StudiengangServiceTest {

    @Mock
    private StudiengangRepository studiengangRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudiengangService studiengangService;

    private TestData testData;

    @BeforeEach
    void setUp() {
        testData = new TestData();
        testData.setUp();
    }

    @Test
    void givenStudiengaengeExist_thenAllStudiengaengeReturned() {
        // Arrange
        Studiengang studiengang1 = new Studiengang();
        studiengang1.setStudiengangNr(1);
        studiengang1.setName("Informatik");

        Studiengang studiengang2 = new Studiengang();
        studiengang2.setStudiengangNr(2);
        studiengang2.setName("Wirtschaftsinformatik");

        List<Studiengang> studiengaenge = List.of(studiengang1, studiengang2);
        when(studiengangRepository.findAll()).thenReturn(studiengaenge);

        // Act
        List<Studiengang> result = studiengangService.getStudiengaenge();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(studiengang1));
        assertTrue(result.contains(studiengang2));
        verify(studiengangRepository).findAll();
    }

    @Test
    void givenExistingStudiengangWithoutStudents_thenStudiengangDeleted() {
        // Arrange
        int studiengangId = 1;
        Studiengang studiengang = new Studiengang();
        studiengang.setStudiengangNr(studiengangId);
        studiengang.setName("Informatik");

        when(studiengangRepository.findById(studiengangId)).thenReturn(Optional.of(studiengang));
        when(studentRepository.findByStudiengaengeContaining(studiengang)).thenReturn(List.of());

        // Act
        studiengangService.deleteStudiengang(studiengangId);

        // Assert
        verify(studiengangRepository).findById(studiengangId);
        verify(studentRepository).findByStudiengaengeContaining(studiengang);
        verify(studiengangRepository).delete(studiengang);
    }

    @Test
    void givenNonExistingStudiengangId_thenNotFoundExceptionThrown() {
        // Arrange
        int studiengangId = 99;
        when(studiengangRepository.findById(studiengangId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> studiengangService.deleteStudiengang(studiengangId));
        verify(studiengangRepository).findById(studiengangId);
        verify(studentRepository, never()).findByStudiengaengeContaining(any());
        verify(studiengangRepository, never()).delete(any());
    }

    @Test
    void givenStudiengangWithStudents_thenConflictExceptionThrown() {
        // Arrange
        int studiengangId = 2;
        Studiengang studiengang = new Studiengang();
        studiengang.setStudiengangNr(studiengangId);
        studiengang.setName("Wirtschaftsinformatik");

        when(studiengangRepository.findById(studiengangId)).thenReturn(Optional.of(studiengang));
        when(studentRepository.findByStudiengaengeContaining(studiengang)).thenReturn(List.of(new Student()));

        // Act & Assert
        assertThrows(ConflictException.class, () -> studiengangService.deleteStudiengang(studiengangId));
        verify(studiengangRepository).findById(studiengangId);
        verify(studentRepository).findByStudiengaengeContaining(studiengang);
        verify(studiengangRepository, never()).delete(any());
    }

    @Test
    void givenValidStudiengang_thenStudiengangCreatedAndIdReturned() {
        // Arrange
        Studiengang studiengang = new Studiengang();
        studiengang.setName("Mathematik");

        Studiengang savedStudiengang = new Studiengang();
        savedStudiengang.setStudiengangNr(42);
        savedStudiengang.setName("Mathematik");

        when(studiengangRepository.save(studiengang)).thenReturn(savedStudiengang);

        // Act
        int result = studiengangService.createStudiengang(studiengang);

        // Assert
        assertEquals(42, result);
        verify(studiengangRepository).save(studiengang);
    }

}
