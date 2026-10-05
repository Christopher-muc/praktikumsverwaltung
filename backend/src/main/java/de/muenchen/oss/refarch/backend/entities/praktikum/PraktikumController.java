package de.muenchen.oss.refarch.backend.entities.praktikum;

import de.muenchen.oss.refarch.backend.entities.praktikum.dto.FullPraktikumDTO;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.PraktikumDTO;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.PraktikumMapper;
import de.muenchen.oss.refarch.backend.entities.praktikum.dto.PraktikumUpdateDTO;
import de.muenchen.oss.refarch.backend.security.Authorities;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PraktikumController {

    private final PraktikumService service;
    private final PraktikumMapper mapper;

    @PreAuthorize(Authorities.Praktikum.GET_ONE_BY_ID)
    @GetMapping("/praktikum/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FullPraktikumDTO getPraktikum(@PathVariable final int id) {
        return mapper.toFullPraktikumDTO(service.getPraktikum(id));
    }

    @PreAuthorize(Authorities.Praktikum.POST)
    @PostMapping("/praktikum")
    @ResponseStatus(HttpStatus.OK)
    public Integer createPraktikum(@RequestBody @Valid final PraktikumDTO dto) {
        return service.createPraktikum(mapper.toPraktikum(dto));
    }

    @PreAuthorize(Authorities.Praktikum.PUT_BY_ID)
    @PutMapping("/praktikum/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updatePraktikum(@PathVariable final int id, @RequestBody @Valid final PraktikumUpdateDTO dto) {
        service.updatePraktikum(id, mapper.toPraktikum(dto));
    }

    @PreAuthorize(Authorities.Praktikum.DELETE_BY_ID)
    @DeleteMapping("/praktikum/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePraktikum(@PathVariable final int id) {
        service.deletePraktikum(id);
    }
}
