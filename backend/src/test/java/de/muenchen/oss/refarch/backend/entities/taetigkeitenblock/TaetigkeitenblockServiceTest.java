package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.BadInputException;
import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.praktikum.PraktikumRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaetigkeitenblockServiceTest {

    @Mock
    private TaetigkeitenblockRepository taetigkeitenblockRepository;

    @Mock
    private PraktikumRepository praktikumRepository;

    @InjectMocks
    private TaetigkeitenblockService service;

    private Praktikum praktikum;

    @BeforeEach
    void setUp() {
        praktikum = new Praktikum();
        praktikum.setBeginnDatum(LocalDate.of(2026, 1, 1));
        praktikum.setEndeDatum(LocalDate.of(2026, 12, 31));
    }

    @Test
    void givenValidBlockOnCreate_thenSavesBlock() {
        final LocalDate tag = LocalDate.of(2026, 5, 10);

        final Taetigkeitenblock block = createBlock(
                1,
                tag,
                LocalTime.of(8, 0),
                LocalTime.of(9, 0));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        when(taetigkeitenblockRepository
                .findByTaetigkeitenblockIDTagAndTaetigkeitenblockIDStudentId(tag, 1))
                .thenReturn(List.of());

        service.createTaetigkeitenblock(block);

        verify(taetigkeitenblockRepository).save(block);
    }

    @Test
    void givenExistingBlockOnCreateOverlap_thenThrowsBadInputException() {
        final LocalDate tag = LocalDate.of(2026, 5, 10);

        final Taetigkeitenblock existingBlock = createBlock(
                1,
                tag,
                LocalTime.of(8, 0),
                LocalTime.of(9, 0));

        final Taetigkeitenblock newBlock = createBlock(
                1,
                tag,
                LocalTime.of(8, 59),
                LocalTime.of(10, 0));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        when(taetigkeitenblockRepository
                .findByTaetigkeitenblockIDTagAndTaetigkeitenblockIDStudentId(tag, 1))
                .thenReturn(List.of(existingBlock));

        assertThrows(
                BadInputException.class,
                () -> service.createTaetigkeitenblock(newBlock));

        verify(taetigkeitenblockRepository, never())
                .save(any(Taetigkeitenblock.class));
    }

    @Test
    void givenExistingBlockEndingAtNewStart_thenSavesBlock() {
        final LocalDate tag = LocalDate.of(2026, 5, 10);

        final Taetigkeitenblock existingBlock = createBlock(
                1,
                tag,
                LocalTime.of(8, 0),
                LocalTime.of(9, 0));

        final Taetigkeitenblock newBlock = createBlock(
                1,
                tag,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        when(taetigkeitenblockRepository
                .findByTaetigkeitenblockIDTagAndTaetigkeitenblockIDStudentId(tag, 1))
                .thenReturn(List.of(existingBlock));

        service.createTaetigkeitenblock(newBlock);

        verify(taetigkeitenblockRepository).save(newBlock);
    }

    @Test
    void givenExistingBlockStartingAtNewEnd_thenSavesBlock() {
        final LocalDate tag = LocalDate.of(2026, 5, 10);

        final Taetigkeitenblock existingBlock = createBlock(
                1,
                tag,
                LocalTime.of(9, 0),
                LocalTime.of(10, 0));

        final Taetigkeitenblock newBlock = createBlock(
                1,
                tag,
                LocalTime.of(8, 0),
                LocalTime.of(9, 0));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        when(taetigkeitenblockRepository
                .findByTaetigkeitenblockIDTagAndTaetigkeitenblockIDStudentId(tag, 1))
                .thenReturn(List.of(existingBlock));

        service.createTaetigkeitenblock(newBlock);

        verify(taetigkeitenblockRepository).save(newBlock);
    }

    @Test
    void givenDateBeforeInternshipOnCreate_thenThrowsBadInputException() {
        final LocalDate tag = LocalDate.of(2025, 12, 31);

        final Taetigkeitenblock block = createBlock(
                1,
                tag,
                LocalTime.of(8, 0),
                LocalTime.of(9, 0));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        assertThrows(
                BadInputException.class,
                () -> service.createTaetigkeitenblock(block));

        verify(taetigkeitenblockRepository, never())
                .save(any(Taetigkeitenblock.class));
    }

    @Test
    void givenDateAfterInternshipOnCreate_thenThrowsBadInputException() {
        final LocalDate tag = LocalDate.of(2027, 1, 1);

        final Taetigkeitenblock block = createBlock(
                1,
                tag,
                LocalTime.of(8, 0),
                LocalTime.of(9, 0));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(praktikum);

        assertThrows(
                BadInputException.class,
                () -> service.createTaetigkeitenblock(block));

        verify(taetigkeitenblockRepository, never())
                .save(any(Taetigkeitenblock.class));
    }

    @Test
    void givenNoPraktikumOnCreate_thenThrowsBadInputException() {
        final LocalDate tag = LocalDate.of(2026, 5, 10);

        final Taetigkeitenblock block = createBlock(
                1,
                tag,
                LocalTime.of(8, 0),
                LocalTime.of(9, 0));

        when(praktikumRepository.getPraktikumByStudentId(1))
                .thenReturn(null);

        assertThrows(
                BadInputException.class,
                () -> service.createTaetigkeitenblock(block));

        verify(taetigkeitenblockRepository, never())
                .save(any(Taetigkeitenblock.class));
    }

    private Taetigkeitenblock createBlock(
            final int studentId,
            final LocalDate tag,
            final LocalTime beginnZeit,
            final LocalTime endeZeit) {

        final TaetigkeitenblockID id = new TaetigkeitenblockID();
        id.setStudentId(studentId);
        id.setTag(tag);
        id.setBeginnZeit(beginnZeit);
        id.setEndeZeit(endeZeit);

        final Taetigkeitenblock block = new Taetigkeitenblock();
        block.setTaetigkeitenblockID(id);

        return block;
    }
}
