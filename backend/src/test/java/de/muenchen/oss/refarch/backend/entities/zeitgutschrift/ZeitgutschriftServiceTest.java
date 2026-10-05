package de.muenchen.oss.refarch.backend.entities.zeitgutschrift;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.BadInputException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.praktikum.PraktikumRepository;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto.ZeitgutschriftCreateDTO;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto.ZeitgutschriftUpdateDTO;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ZeitgutschriftServiceTest {

    @Mock
    private ZeitgutschriftRepository repository;

    @Mock
    private PraktikumRepository praktikumRepository;

    @InjectMocks
    private ZeitgutschriftService service;

    private Praktikum praktikum;

    @BeforeEach
    void setUp() {
        praktikum = new Praktikum();
        praktikum.setBeginnDatum(LocalDate.of(2026, 1, 1));
        praktikum.setEndeDatum(LocalDate.of(2026, 12, 31));
    }

    @Test
    void givenValidZeitgutschriftOnCreate_thenSavesZeitgutschrift() {
        final ZeitgutschriftCreateDTO dto = new ZeitgutschriftCreateDTO(
                LocalDate.of(2026, 5, 10),
                60,
                "Test",
                1);

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        when(repository.save(any(Zeitgutschrift.class)))
                .thenAnswer(invocation -> {
                    final Zeitgutschrift zeitgutschrift = invocation.getArgument(0);
                    zeitgutschrift.setId(42);
                    return zeitgutschrift;
                });

        final int id = service.createZeitgutschriftFromCreationDto(dto);

        assertEquals(42, id);

        verify(repository).save(any(Zeitgutschrift.class));
    }

    @Test
    void givenMissingPraktikumOnCreate_thenThrowsNotFoundException() {
        final ZeitgutschriftCreateDTO dto = new ZeitgutschriftCreateDTO(
                LocalDate.of(2026, 5, 10),
                60,
                "Test",
                1);

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> service.createZeitgutschriftFromCreationDto(dto));

        verify(repository, never()).save(any(Zeitgutschrift.class));
    }

    @Test
    void givenDateBeforePraktikumOnCreate_thenThrowsBadInputException() {
        final ZeitgutschriftCreateDTO dto = new ZeitgutschriftCreateDTO(
                LocalDate.of(2025, 12, 31),
                60,
                "Test",
                1);

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        assertThrows(
                BadInputException.class,
                () -> service.createZeitgutschriftFromCreationDto(dto));

        verify(repository, never()).save(any(Zeitgutschrift.class));
    }

    @Test
    void givenDateAfterPraktikumOnCreate_thenThrowsBadInputException() {
        final ZeitgutschriftCreateDTO dto = new ZeitgutschriftCreateDTO(
                LocalDate.of(2027, 1, 1),
                60,
                "Test",
                1);

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        assertThrows(
                BadInputException.class,
                () -> service.createZeitgutschriftFromCreationDto(dto));

        verify(repository, never()).save(any(Zeitgutschrift.class));
    }

    @Test
    void givenExistingZeitgutschriftOnUpdate_thenUpdatesAndSavesZeitgutschrift() {
        final Zeitgutschrift zeitgutschrift = new Zeitgutschrift();
        zeitgutschrift.setId(10);

        final ZeitgutschriftUpdateDTO dto = new ZeitgutschriftUpdateDTO(
                LocalDate.of(2026, 6, 1),
                120,
                "Neuer Grund",
                1,
                10);

        when(repository.findById(10))
                .thenReturn(Optional.of(zeitgutschrift));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        service.updateZeitgutschriftFromCreationDto(dto);

        assertEquals(LocalDate.of(2026, 6, 1), zeitgutschrift.getTag());
        assertEquals(120, zeitgutschrift.getMengeMinuten());
        assertEquals("Neuer Grund", zeitgutschrift.getGrund());
        assertEquals(praktikum, zeitgutschrift.getPraktikum());

        verify(repository).save(zeitgutschrift);
    }

    @Test
    void givenMissingZeitgutschriftOnUpdate_thenThrowsNotFoundException() {
        final ZeitgutschriftUpdateDTO dto = new ZeitgutschriftUpdateDTO(
                LocalDate.of(2026, 6, 1),
                120,
                "Test",
                1,
                10);

        when(repository.findById(10))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.updateZeitgutschriftFromCreationDto(dto));

        verify(repository, never()).save(any(Zeitgutschrift.class));
    }

    @Test
    void givenMissingPraktikumOnUpdate_thenThrowsNotFoundException() {
        final Zeitgutschrift zeitgutschrift = new Zeitgutschrift();

        final ZeitgutschriftUpdateDTO dto = new ZeitgutschriftUpdateDTO(
                LocalDate.of(2026, 6, 1),
                120,
                "Test",
                1,
                10);

        when(repository.findById(10))
                .thenReturn(Optional.of(zeitgutschrift));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(null);

        assertThrows(
                NotFoundException.class,
                () -> service.updateZeitgutschriftFromCreationDto(dto));

        verify(repository, never()).save(any(Zeitgutschrift.class));
    }

    @Test
    void givenDateBeforePraktikumOnUpdate_thenThrowsBadInputException() {
        final Zeitgutschrift zeitgutschrift = new Zeitgutschrift();

        final ZeitgutschriftUpdateDTO dto = new ZeitgutschriftUpdateDTO(
                LocalDate.of(2025, 12, 31),
                120,
                "Test",
                1,
                10);

        when(repository.findById(10))
                .thenReturn(Optional.of(zeitgutschrift));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        assertThrows(
                BadInputException.class,
                () -> service.updateZeitgutschriftFromCreationDto(dto));

        verify(repository, never()).save(any(Zeitgutschrift.class));
    }

    @Test
    void givenDateAfterPraktikumOnUpdate_thenThrowsBadInputException() {
        final Zeitgutschrift zeitgutschrift = new Zeitgutschrift();

        final ZeitgutschriftUpdateDTO dto = new ZeitgutschriftUpdateDTO(
                LocalDate.of(2027, 1, 1),
                120,
                "Test",
                1,
                10);

        when(repository.findById(10))
                .thenReturn(Optional.of(zeitgutschrift));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        assertThrows(
                BadInputException.class,
                () -> service.updateZeitgutschriftFromCreationDto(dto));

        verify(repository, never()).save(any(Zeitgutschrift.class));
    }

    @Test
    void givenExistingZeitgutschriftOnDelete_thenDeletesZeitgutschrift() {
        final Zeitgutschrift zeitgutschrift = new Zeitgutschrift();
        zeitgutschrift.setId(10);

        when(repository.findById(10))
                .thenReturn(Optional.of(zeitgutschrift));

        service.deleteZeitgutschrift(10);

        verify(repository).delete(zeitgutschrift);
    }

    @Test
    void givenMissingZeitgutschriftOnDelete_thenThrowsNotFoundException() {
        when(repository.findById(10))
                .thenReturn(Optional.empty());

        assertThrows(
                NotFoundException.class,
                () -> service.deleteZeitgutschrift(10));

        verify(repository, never()).delete(any(Zeitgutschrift.class));
    }
}
