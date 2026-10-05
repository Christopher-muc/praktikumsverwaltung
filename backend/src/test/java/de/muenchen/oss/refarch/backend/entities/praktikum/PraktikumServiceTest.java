package de.muenchen.oss.refarch.backend.entities.praktikum;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ConflictException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.TestData;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.FullPraktikumAggregat;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import de.muenchen.oss.refarch.backend.entities.student.StudentRepository;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.Taetigkeitenblock;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.TaetigkeitenblockRepository;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.Zeitgutschrift;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.ZeitgutschriftRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PraktikumServiceTest {

    @Mock
    private PraktikumRepository praktikumRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TaetigkeitenblockRepository taetigkeitenblockRepository;

    @Mock
    private ZeitgutschriftRepository zeitgutschriftRepository;

    @InjectMocks
    private PraktikumService praktikumService;

    TestData testData;

    @BeforeEach
    void setUp() {
        testData = new TestData();
        testData.setUp();
    }

    @Test
    void givenExistingPraktikum_thenFullPraktikumAggregatReturned() {
        setUp();
        int studentId = testData.getStudent1().getStudentId();

        when(praktikumRepository.findById(studentId)).thenReturn(Optional.of(testData.getPraktikum1()));
        when(taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentId)).thenReturn(Collections.emptyList());
        when(zeitgutschriftRepository.findByPraktikumStudentId(studentId)).thenReturn(Collections.emptyList());

        // Act
        FullPraktikumAggregat result = praktikumService.getPraktikum(studentId);

        // Assert
        assertNotNull(result);
        assertEquals(testData.getPraktikum1().getStudentId(), result.getStudentId());
        assertEquals(testData.getPraktikum1().getBeginnDatum(), result.getBeginnDatum());
        assertEquals(testData.getPraktikum1().getEndeDatum(), result.getEndeDatum());
        assertEquals(testData.getPraktikum1().getWochenarbeitszeit(), result.getWochenarbeitszeit());
        assertEquals(testData.getPraktikum1().getBenoetigteWochen(), result.getBenoetigteWochen());
        assertTrue(result.getTaetigkeiten().isEmpty());
        assertTrue(result.getZeitgutschriften().isEmpty());

        verify(praktikumRepository).findById(studentId);
        verify(taetigkeitenblockRepository).findByTaetigkeitenblockIDStudentId(studentId);
        verify(zeitgutschriftRepository).findByPraktikumStudentId(studentId);
    }

    @Test
    void givenStudentWithoutPraktikum_thenPraktikumCreated() {
        // Arrange
        TestData testData = new TestData();
        testData.setUp();
        Praktikum praktikum = testData.getPraktikum1();
        Student student = testData.getStudent1();
        student.setPraktikum(null); // Student hat noch kein Praktikum

        when(studentRepository.save(student)).thenReturn(student);
        when(praktikumRepository.save(praktikum)).thenReturn(praktikum);

        // Act
        Integer result = praktikumService.createPraktikum(praktikum);

        // Assert
        assertEquals(praktikum.getStudentId(), result);
        assertEquals(praktikum, student.getPraktikum());
        verify(studentRepository).save(student);
        verify(praktikumRepository).save(praktikum);
    }

    @Test
    void givenStudentWithExistingPraktikum_thenConflictExceptionThrown() {
        // Arrange
        TestData testData = new TestData();
        testData.setUp();
        Praktikum praktikum = testData.getPraktikum1();
        Student student = testData.getStudent1();
        student.setPraktikum(new Praktikum()); // Student hat schon ein Praktikum

        // Act & Assert
        assertThrows(ConflictException.class, () -> praktikumService.createPraktikum(praktikum));
        verify(studentRepository, never()).save(any());
        verify(praktikumRepository, never()).save(any());
    }

    @Test
    void givenExistingPraktikum_thenPraktikumUpdated() {
        // Arrange
        TestData testData = new TestData();
        testData.setUp();
        int studentId = testData.getStudent1().getStudentId();
        Praktikum existingPraktikum = testData.getPraktikum1();
        Student student = testData.getStudent1();

        Praktikum neuesPraktikum = new Praktikum();
        neuesPraktikum.setBeginnDatum(existingPraktikum.getBeginnDatum().plusDays(1));
        neuesPraktikum.setEndeDatum(existingPraktikum.getEndeDatum().plusDays(1));
        neuesPraktikum.setWochenarbeitszeit(35);
        neuesPraktikum.setBenoetigteWochen(10);

        when(praktikumRepository.findById(studentId)).thenReturn(Optional.of(existingPraktikum));
        when(studentRepository.save(student)).thenReturn(student);
        when(praktikumRepository.save(neuesPraktikum)).thenReturn(neuesPraktikum);

        // Act
        praktikumService.updatePraktikum(studentId, neuesPraktikum);

        // Assert
        assertEquals(student, neuesPraktikum.getStudent());
        assertEquals(studentId, neuesPraktikum.getStudentId());
        assertEquals(neuesPraktikum, student.getPraktikum());
        verify(studentRepository).save(student);
        verify(praktikumRepository).save(neuesPraktikum);
    }

    @Test
    void givenNonExistingPraktikumForUpdate_thenNotFoundExceptionThrown() {
        // Arrange
        int studentId = 999;
        Praktikum neuesPraktikum = new Praktikum();
        when(praktikumRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> praktikumService.updatePraktikum(studentId, neuesPraktikum));
        verify(studentRepository, never()).save(any());
        verify(praktikumRepository, never()).save(neuesPraktikum);
    }

    @Test
    void givenExistingPraktikum_thenPraktikumAndRelatedEntitiesDeleted() {
        // Arrange
        TestData testData = new TestData();
        testData.setUp();
        int studentId = testData.getStudent1().getStudentId();
        Praktikum praktikum = testData.getPraktikum1();
        Student student = testData.getStudent1();

        List<Zeitgutschrift> zeitgutschriften = List.of();
        List<Taetigkeitenblock> taetigkeitenbloecke = List.of();

        when(zeitgutschriftRepository.findByPraktikumStudentId(studentId)).thenReturn(zeitgutschriften);
        when(taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentId)).thenReturn(taetigkeitenbloecke);
        when(praktikumRepository.findById(studentId)).thenReturn(Optional.of(praktikum));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        when(studentRepository.save(student)).thenReturn(student);

        // Act
        praktikumService.deletePraktikum(studentId);

        // Assert
        assertNull(student.getPraktikum());
        verify(zeitgutschriftRepository).deleteAll(zeitgutschriften);
        verify(taetigkeitenblockRepository).deleteAll(taetigkeitenbloecke);
        verify(praktikumRepository).findById(studentId);
        verify(studentRepository).findById(studentId);
        verify(studentRepository).save(student);
        verify(praktikumRepository).delete(praktikum);
    }

    @Test
    void givenNonExistingPraktikumForDelete_thenNotFoundExceptionThrown() {
        // Arrange
        int studentId = 999;
        when(zeitgutschriftRepository.findByPraktikumStudentId(studentId)).thenReturn(List.of());
        when(taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentId)).thenReturn(List.of());
        when(praktikumRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> praktikumService.deletePraktikum(studentId));
        verify(zeitgutschriftRepository).deleteAll(List.of());
        verify(taetigkeitenblockRepository).deleteAll(List.of());
        verify(praktikumRepository).findById(studentId);
        verify(studentRepository, never()).findById(anyInt());
        verify(studentRepository, never()).save(any());
        verify(praktikumRepository, never()).delete(any());
    }

    @Test
    void givenExistingPraktikumButStudentNotFound_thenNotFoundExceptionThrown() {
        // Arrange
        TestData testData = new TestData();
        testData.setUp();
        int studentId = testData.getStudent1().getStudentId();
        Praktikum praktikum = testData.getPraktikum1();

        when(zeitgutschriftRepository.findByPraktikumStudentId(studentId)).thenReturn(List.of());
        when(taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentId)).thenReturn(List.of());
        when(praktikumRepository.findById(studentId)).thenReturn(Optional.of(praktikum));
        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> praktikumService.deletePraktikum(studentId));
        verify(zeitgutschriftRepository).deleteAll(List.of());
        verify(taetigkeitenblockRepository).deleteAll(List.of());
        verify(praktikumRepository).findById(studentId);
        verify(studentRepository).findById(studentId);
        verify(studentRepository, never()).save(any());
        verify(praktikumRepository, never()).delete(any());
    }

}
