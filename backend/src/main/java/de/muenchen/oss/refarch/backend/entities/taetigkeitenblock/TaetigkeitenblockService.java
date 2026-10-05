package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.DATE_NOT_DURING_INTERNSHIP;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.PRAKTIKUM_NOT_FOUND;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.TAETIGKEIT_NOT_FOUND;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.BadInputException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.praktikum.PraktikumRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TaetigkeitenblockService {

    private final TaetigkeitenblockRepository taetigkeitenblockRepository;

    private final PraktikumRepository praktikumRepository;

    public List<Taetigkeitenblock> getTaetigkeitbyDay(
            final int studentid,
            final LocalDate date) {

        final List<Taetigkeitenblock> bloecke = taetigkeitenblockRepository
                .findByTaetigkeitenblockIDTagAndTaetigkeitenblockIDStudentId(
                        date,
                        studentid);

        if (bloecke.isEmpty()) {
            throw new NotFoundException(
                    String.format(TAETIGKEIT_NOT_FOUND, studentid, date));
        }

        return bloecke;
    }

    public void createTaetigkeitenblock(final Taetigkeitenblock entity) {
        checkIfDayDuringInternship(entity);
        checkForOverlap(entity, null);

        taetigkeitenblockRepository.save(entity);
    }

    public Taetigkeitenblock updateTaetigkeitenblock(
            final TaetigkeitenblockID id,
            final Taetigkeitenblock entity) {

        final Taetigkeitenblock block = taetigkeitenblockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(
                                TAETIGKEIT_NOT_FOUND,
                                id.getStudentId(),
                                id.getTag())));

        checkIfDayDuringInternship(entity);
        checkForOverlap(entity, id);

        taetigkeitenblockRepository.delete(block);
        taetigkeitenblockRepository.save(entity);

        return entity;
    }

    public void deleteTaetigkeitenblock(final TaetigkeitenblockID id) {
        final Taetigkeitenblock block = taetigkeitenblockRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(
                                TAETIGKEIT_NOT_FOUND,
                                id.getStudentId(),
                                id.getTag())));

        taetigkeitenblockRepository.delete(block);
    }

    private void checkForOverlap(
            final Taetigkeitenblock entity,
            final TaetigkeitenblockID excludedId) {

        final TaetigkeitenblockID newId = entity.getTaetigkeitenblockID();

        final List<Taetigkeitenblock> existingBlocks = taetigkeitenblockRepository
                .findByTaetigkeitenblockIDTagAndTaetigkeitenblockIDStudentId(
                        newId.getTag(),
                        newId.getStudentId());

        for (final Taetigkeitenblock existing : existingBlocks) {
            final TaetigkeitenblockID existingId = existing.getTaetigkeitenblockID();

            if (existingId.equals(excludedId)) {
                continue;
            }

            final boolean overlaps = newId.getBeginnZeit().isBefore(existingId.getEndeZeit())
                    && newId.getEndeZeit().isAfter(existingId.getBeginnZeit());

            if (overlaps) {
                throw new BadInputException(
                        "Tätigkeitsblöcke dürfen sich nicht überschneiden.");
            }
        }
    }

    private void checkIfDayDuringInternship(
            final Taetigkeitenblock entity) {

        final Praktikum praktikum = praktikumRepository.getPraktikumByStudentId(
                entity.getTaetigkeitenblockID().getStudentId());

        if (praktikum == null) {
            throw new BadInputException(PRAKTIKUM_NOT_FOUND);
        }

        if (entity.getTaetigkeitenblockID().getTag()
                .isAfter(praktikum.getEndeDatum())
                || entity.getTaetigkeitenblockID().getTag()
                        .isBefore(praktikum.getBeginnDatum())) {

            throw new BadInputException(
                    DATE_NOT_DURING_INTERNSHIP);
        }
    }
}
