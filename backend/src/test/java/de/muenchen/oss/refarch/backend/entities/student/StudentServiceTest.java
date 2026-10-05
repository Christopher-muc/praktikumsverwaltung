package de.muenchen.oss.refarch.backend.entities.student;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDENT_NOT_FOUND;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.TestData;
import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.praktikum.PraktikumRepository;
import de.muenchen.oss.refarch.backend.entities.student.dto.CreateUpdateStudentRequestDTO;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.Taetigkeitenblock;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.TaetigkeitenblockRepository;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.Zeitgutschrift;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.ZeitgutschriftRepository;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private PraktikumRepository praktikumRepository;

    @Mock
    private ZeitgutschriftRepository zeitgutschriftRepository;

    @Mock
    private TaetigkeitenblockRepository taetigkeitenblockRepository;

    @InjectMocks
    private StudentService testedService;

    TestData data;

    @BeforeEach
    void setUp() {
        data = new TestData();
        data.setUp();
    }

    @Test
    void givenGetAll_thenReturnAll() {
        when(studentRepository.findAll()).thenReturn(List.of(data.getStudent1(), data.getStudent2(), data.getStudent3()));

        List<Student> result = testedService.getAllStudents();

        verify(studentRepository).findAll();
        assertThat(result).usingRecursiveAssertion().isEqualTo(List.of(data.getStudent1(), data.getStudent2(), data.getStudent3()));
    }

    @Test
    void givenStudentCreateStudent_thenReturnId() {
        AtomicInteger idGenerator = new AtomicInteger(100);

        when(studentRepository.save(any(Student.class))).thenAnswer(invocation -> {
            Student input = invocation.getArgument(0);
            input.setStudentId(idGenerator.getAndIncrement());
            return input;
        });

        int result1 = testedService.createStudent(data.getStudent1());
        int result2 = testedService.createStudent(data.getStudent2());

        verify(studentRepository, times(2)).save(any(Student.class));

        assertThat(result1).isEqualTo(100);
        assertThat(result2).isEqualTo(101);

    }

    @Test
    void givenIdGetStudentWhenExists_thenReturnStudent() {
        int studentId = 100;
        Student student = data.getStudent1();
        student.setStudentId(studentId);

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        Student result = testedService.getStudent(studentId);

        verify(studentRepository).findById(studentId);
        assertThat(result).usingRecursiveAssertion().isEqualTo(student);
    }

    @Test
    void givenIdGetStudentWhenNotExists_thenThrowNotFoundException() {
        int studentId = 999;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testedService.getStudent(studentId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format(STUDENT_NOT_FOUND, studentId));

        verify(studentRepository).findById(studentId);
    }

    @Test
    void givenIdDeleteStudentAndRelatedDataWhenStudentExists_thenDeletesAllRelatedData() {
        int studentId = 100;
        Student student = data.getStudent1();
        student.setStudentId(studentId);

        Praktikum praktikum = new Praktikum();
        praktikum.setStudentId(studentId);

        List<Zeitgutschrift> zeitgutschriften = List.of(new Zeitgutschrift(), new Zeitgutschrift());
        List<Taetigkeitenblock> taetigkeitenbloecke = List.of(new Taetigkeitenblock());

        when(zeitgutschriftRepository.findByPraktikumStudentId(studentId)).thenReturn(zeitgutschriften);
        when(taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentId)).thenReturn(taetigkeitenbloecke);
        when(praktikumRepository.findById(studentId)).thenReturn(Optional.of(praktikum));
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));

        testedService.deleteStudentAndRelatedData(studentId);

        verify(zeitgutschriftRepository).findByPraktikumStudentId(studentId);
        verify(taetigkeitenblockRepository).findByTaetigkeitenblockIDStudentId(studentId);
        verify(zeitgutschriftRepository).deleteAll(zeitgutschriften);
        verify(taetigkeitenblockRepository).deleteAll(taetigkeitenbloecke);
        verify(praktikumRepository).findById(studentId);
        verify(praktikumRepository).delete(praktikum);
        verify(studentRepository).findById(studentId);
        verify(studentRepository).delete(student);
    }

    @Test
    void givenIdDeleteStudentAndRelatedDataWhenStudentNotExists_thenThrowNotFoundException() {
        int studentId = 999;

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testedService.deleteStudentAndRelatedData(studentId))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format(STUDENT_NOT_FOUND, studentId));

        verify(studentRepository).findById(studentId);
        // Optional: Die anderen Repositories sollten nicht aufgerufen werden
        verifyNoInteractions(zeitgutschriftRepository, taetigkeitenblockRepository, praktikumRepository);
    }

    @Test
    void givenIdDeleteStudentAndRelatedDataWhenStudentHasNoPraktikum_thenDeleteWithoutError() {
        int studentId = 123;
        Student student = new Student();
        student.setStudentId(studentId);
        student.setPraktikum(null); // explizit kein Praktikum

        // Student existiert
        when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
        // Kein Praktikum vorhanden
        when(praktikumRepository.findById(studentId)).thenReturn(Optional.empty());
        // Leere Listen für die anderen Entitäten
        when(zeitgutschriftRepository.findByPraktikumStudentId(studentId)).thenReturn(List.of());
        when(taetigkeitenblockRepository.findByTaetigkeitenblockIDStudentId(studentId)).thenReturn(List.of());

        // Ausführung
        testedService.deleteStudentAndRelatedData(studentId);

        // Verifikation
        verify(studentRepository).findById(studentId);
        verify(praktikumRepository).findById(studentId);
        verify(zeitgutschriftRepository).findByPraktikumStudentId(studentId);
        verify(taetigkeitenblockRepository).findByTaetigkeitenblockIDStudentId(studentId);
        verify(zeitgutschriftRepository).deleteAll(List.of());
        verify(taetigkeitenblockRepository).deleteAll(List.of());
        verify(studentRepository).delete(student);

        // Es darf KEIN Praktikum gelöscht werden
        verify(praktikumRepository, never()).delete(any());
    }

    @Test
    void givenIdUpdateStudentWhenStudentExists_thenUpdateAndReturnStudent() {
        int studentId = 1;
        Student existingStudent = new Student();
        existingStudent.setStudentId(studentId);
        existingStudent.setVorname("Alt");
        existingStudent.setNachname("Name");

        CreateUpdateStudentRequestDTO dto = new CreateUpdateStudentRequestDTO();
        dto.setVorname("Neu");
        dto.setNachname("Nachname");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(existingStudent)).thenReturn(existingStudent);

        Student result = testedService.updateStudent(studentId, dto);

        assertThat(result.getVorname()).isEqualTo("Neu");
        assertThat(result.getNachname()).isEqualTo("Nachname");
        verify(studentRepository).findById(studentId);
        verify(studentRepository).save(existingStudent);
    }

    @Test
    void givenIdUpdateStudentWhenStudentNotExists_thenThrowNotFoundException() {
        int studentId = 2;
        CreateUpdateStudentRequestDTO dto = new CreateUpdateStudentRequestDTO();
        dto.setVorname("Test");
        dto.setNachname("Test");

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> testedService.updateStudent(studentId, dto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format(STUDENT_NOT_FOUND, studentId));

        verify(studentRepository).findById(studentId);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void givenIdUpdateStudentWhenStudentNotFound_thenThrowNotFoundException() {
        // Arrange
        int studentId = 42;
        CreateUpdateStudentRequestDTO dto = new CreateUpdateStudentRequestDTO();
        dto.setVorname("Max");
        dto.setNachname("Mustermann");

        when(studentRepository.findById(studentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> testedService.updateStudent(studentId, dto))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining(String.format(STUDENT_NOT_FOUND, studentId));

        verify(studentRepository).findById(studentId);
        verify(studentRepository, never()).save(any());
    }

    @Test
    void givenStringFindStudentsByNameWhenVornameIsBlank_thenSearchByNachname() {
        String vorname = " ";
        String nachname = "Müller";
        List<Student> expected = List.of(new Student());

        when(studentRepository.findByNachnameContainingIgnoreCase(nachname)).thenReturn(expected);

        List<Student> result = testedService.findStudentsByName(vorname, nachname);

        assertThat(result).isEqualTo(expected);
        verify(studentRepository).findByNachnameContainingIgnoreCase(nachname);
        verify(studentRepository, never()).findByVornameContainingIgnoreCase(anyString());
        verify(studentRepository, never()).findByNachnameContainingIgnoreCaseOrVornameContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void givenStringFindStudentsByNameWhenNachnameIsBlank_thenSearchByVorname() {
        String vorname = "Anna";
        String nachname = " ";
        List<Student> expected = List.of(new Student());

        when(studentRepository.findByVornameContainingIgnoreCase(vorname)).thenReturn(expected);

        List<Student> result = testedService.findStudentsByName(vorname, nachname);

        assertThat(result).isEqualTo(expected);
        verify(studentRepository).findByVornameContainingIgnoreCase(vorname);
        verify(studentRepository, never()).findByNachnameContainingIgnoreCase(anyString());
        verify(studentRepository, never()).findByNachnameContainingIgnoreCaseOrVornameContainingIgnoreCase(anyString(), anyString());
    }

    @Test
    void givenStringFindStudentsByNameWhenBothAreSet_thenSearchByBoth() {
        String vorname = "Anna";
        String nachname = "Müller";
        List<Student> expected = List.of(new Student());

        when(studentRepository.findByNachnameContainingIgnoreCaseOrVornameContainingIgnoreCase(nachname, vorname)).thenReturn(expected);

        List<Student> result = testedService.findStudentsByName(vorname, nachname);

        assertThat(result).isEqualTo(expected);
        verify(studentRepository).findByNachnameContainingIgnoreCaseOrVornameContainingIgnoreCase(nachname, vorname);
        verify(studentRepository, never()).findByVornameContainingIgnoreCase(anyString());
        verify(studentRepository, never()).findByNachnameContainingIgnoreCase(anyString());
    }

}
