package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock;

import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.dto.TaetigkeitenblockCreationDTO;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.dto.TaetigkeitenblockDTO;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.dto.TaetigkeitenblockMapper;
import de.muenchen.oss.refarch.backend.security.Authorities;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class TaetigkeitenblockController {

    private final TaetigkeitenblockService taetigkeitenblockService;
    private final TaetigkeitenblockMapper mapper;

    @PreAuthorize(Authorities.Taetigkeitenblock.GET_BY_ID_AND_DAY)
    @GetMapping("/taetigkeitenblock/{studentid}/{tag}")
    public List<TaetigkeitenblockDTO> getTaetigkeitbyDay(@PathVariable final int studentid, @PathVariable final LocalDate tag) {
        return mapper.toDTOs(taetigkeitenblockService.getTaetigkeitbyDay(studentid, tag));
    }

    @PreAuthorize(Authorities.Taetigkeitenblock.POST)
    @PostMapping("/taetigkeitenblock")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTaetigkeitenblock(@RequestBody @Valid final TaetigkeitenblockCreationDTO dto) {
        taetigkeitenblockService.createTaetigkeitenblock(mapper.toEntity(dto));
    }

    @PreAuthorize(Authorities.Taetigkeitenblock.PUT)
    @PutMapping("/taetigkeitenblock")
    @ResponseStatus(HttpStatus.OK)
    public TaetigkeitenblockDTO updateTaetigkeitenblock(@RequestParam final int studentId, @RequestParam final LocalTime beginnZeit,
            @RequestParam final LocalTime endeZeit, @RequestParam final LocalDate tag, @RequestBody @Valid final TaetigkeitenblockCreationDTO dto) {
        return mapper.toDTO(taetigkeitenblockService.updateTaetigkeitenblock(mapper.toEntity(tag, beginnZeit, endeZeit, studentId), mapper.toEntity(dto)));
    }

    @PreAuthorize(Authorities.Taetigkeitenblock.DELETE)
    @DeleteMapping("/taetigkeitenblock")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTaetigkeitenblock(
            @RequestParam final int studentId,
            @RequestParam final LocalTime beginnZeit,
            @RequestParam final LocalTime endeZeit,
            @RequestParam final LocalDate tag) {

        taetigkeitenblockService.deleteTaetigkeitenblock(
                mapper.toEntity(tag, beginnZeit, endeZeit, studentId));
    }

}
