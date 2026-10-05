package de.muenchen.oss.refarch.backend.entities.zeitgutschrift;

import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.DATE_NOT_DURING_INTERNSHIP;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.PRAKTIKUM_NOT_FOUND;
import static de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.ZEITGUTSCHRIFT_NOT_FOUND;

import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.BadInputException;
import de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException;
import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.praktikum.PraktikumRepository;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto.ZeitgutschriftCreateDTO;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto.ZeitgutschriftUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ZeitgutschriftService {

    private final ZeitgutschriftRepository repository;
    private final PraktikumRepository praktikumRepository;

    // Mapping is implemented here because of other needed classes.
    public int createZeitgutschriftFromCreationDto(final ZeitgutschriftCreateDTO dto) {
        final Zeitgutschrift zeitgutschrift = new Zeitgutschrift();
        zeitgutschrift.setGrund(dto.grund());
        zeitgutschrift.setTag(dto.tag());
        zeitgutschrift.setMengeMinuten(dto.mengeMinuten());
        final Praktikum praktikum = praktikumRepository.getPraktikumByStudentId(dto.praktikumID());
        if (praktikum == null) {
            throw new NotFoundException(String.format(PRAKTIKUM_NOT_FOUND, dto.praktikumID()));
        }
        if (dto.tag().isBefore(praktikum.getBeginnDatum()) || dto.tag().isAfter(praktikum.getEndeDatum())) {
            throw new BadInputException(DATE_NOT_DURING_INTERNSHIP);
        }
        zeitgutschrift.setPraktikum(praktikum);
        repository.save(zeitgutschrift);
        return zeitgutschrift.getId();
    }

    public void updateZeitgutschriftFromCreationDto(final ZeitgutschriftUpdateDTO body) {
        final Zeitgutschrift zeitgutschrift = repository.findById(body.zeitgutschriftID())
                .orElseThrow(() -> new NotFoundException(String.format(ZEITGUTSCHRIFT_NOT_FOUND, body.zeitgutschriftID())));

        final Praktikum p = praktikumRepository.getPraktikumByStudentId(body.praktikumID());

        if (p == null) {
            throw new NotFoundException(String.format(PRAKTIKUM_NOT_FOUND, body.praktikumID()));
        }
        if (body.tag().isBefore(p.getBeginnDatum()) || body.tag().isAfter(p.getEndeDatum())) {
            throw new BadInputException(DATE_NOT_DURING_INTERNSHIP);
        }
        zeitgutschrift.setPraktikum(p);
        zeitgutschrift.setMengeMinuten(body.mengeMinuten());
        zeitgutschrift.setTag(body.tag());
        zeitgutschrift.setGrund(body.grund());
        repository.save(zeitgutschrift);
    }

    public void deleteZeitgutschrift(final int zeitgutschriftId) {
        repository.delete(repository.findById(zeitgutschriftId)
                .orElseThrow(() -> new NotFoundException(
                        String.format(ZEITGUTSCHRIFT_NOT_FOUND, zeitgutschriftId))));
    }
}
