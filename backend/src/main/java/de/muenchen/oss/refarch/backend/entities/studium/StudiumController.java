package de.muenchen.oss.refarch.backend.entities.studium;

import de.muenchen.oss.refarch.backend.entities.student.dto.SimpleStudentDTO;
import de.muenchen.oss.refarch.backend.entities.student.dto.StudentMapper;
import de.muenchen.oss.refarch.backend.security.Authorities;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class StudiumController {

    private final StudiumService service;
    private final StudentMapper studentMapper;

    @PreAuthorize(Authorities.Studium.PUT)
    @PutMapping("/studium")
    @ResponseStatus(HttpStatus.OK)
    public void addStudiumToStudent(@RequestBody @Valid final StudiumMappingDTO dto) {
        service.addStudiumToStudent(dto);
    }

    @PreAuthorize(Authorities.Studium.DELETE)
    @DeleteMapping("/studium")
    @ResponseStatus(HttpStatus.OK)
    public void removeStudiumfromStudent(@RequestBody @Valid final StudiumMappingDTO dto) {
        service.removeStudiumFromStudent(dto);
    }

    @PreAuthorize(Authorities.Studium.GET_BY_ID)
    @GetMapping("/studium/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleStudentDTO> getStudentsByStudiengang(@PathVariable final int id) {
        return studentMapper.toSimpleStudentDTO(service.getStudentenPerStudiengang(id));
    }
}
