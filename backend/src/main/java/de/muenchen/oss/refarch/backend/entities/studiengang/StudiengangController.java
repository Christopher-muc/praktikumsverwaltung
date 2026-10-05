package de.muenchen.oss.refarch.backend.entities.studiengang;

import de.muenchen.oss.refarch.backend.entities.studiengang.dto.StudiengangCreationDTO;
import de.muenchen.oss.refarch.backend.entities.studiengang.dto.StudiengangDTO;
import de.muenchen.oss.refarch.backend.entities.studiengang.dto.StudiengangMapper;
import de.muenchen.oss.refarch.backend.security.Authorities;
import jakarta.validation.Valid;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudiengangController {

    protected final StudiengangService service;

    protected final StudiengangMapper mapper;

    @PreAuthorize(Authorities.Studiengang.GET_ALL)
    @GetMapping("/studiengaenge")
    public List<StudiengangDTO> getStudiengaenge() {
        return mapper.toDTO(service.getStudiengaenge());
    }

    @PreAuthorize(Authorities.Studiengang.DELETE_BY_ID)
    @DeleteMapping("/studiengaenge/{id}")
    public void deleteStudiengang(@PathVariable final int id) {
        service.deleteStudiengang(id);
    }

    @PreAuthorize(Authorities.Studiengang.POST)
    @PostMapping("/studiengaenge")
    public int createStudiengang(@RequestBody @Valid final StudiengangCreationDTO dto) {
        return service.createStudiengang(mapper.toEntity(dto));
    }
}
