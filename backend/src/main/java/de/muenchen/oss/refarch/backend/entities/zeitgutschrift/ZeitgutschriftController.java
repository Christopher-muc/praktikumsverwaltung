package de.muenchen.oss.refarch.backend.entities.zeitgutschrift;

import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto.ZeitgutschriftCreateDTO;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto.ZeitgutschriftUpdateDTO;
import de.muenchen.oss.refarch.backend.security.Authorities;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class ZeitgutschriftController {

    private final ZeitgutschriftService service;

    @PreAuthorize(Authorities.Zeitgutschrift.POST)
    @PostMapping("/zeitgutschrift")
    @ResponseStatus(HttpStatus.CREATED)
    public int createZeitgutschrift(@RequestBody @Valid final ZeitgutschriftCreateDTO body) {
        return service.createZeitgutschriftFromCreationDto(body);
    }

    @PreAuthorize(Authorities.Zeitgutschrift.PUT)
    @PutMapping("/zeitgutschrift")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateZeitgutschrift(@RequestBody @Valid final ZeitgutschriftUpdateDTO body) {
        service.updateZeitgutschriftFromCreationDto(body);
    }

    @PreAuthorize(Authorities.Zeitgutschrift.DELETE_BY_ID)
    @DeleteMapping("/zeitgutschrift/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteZeitgutschrift(@PathVariable final int id) {
        service.deleteZeitgutschrift(id);
    }
}
