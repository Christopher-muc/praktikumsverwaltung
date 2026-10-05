package de.muenchen.oss.refarch.backend.entities.student;

import de.muenchen.oss.refarch.backend.entities.student.dto.CreateUpdateStudentRequestDTO;
import de.muenchen.oss.refarch.backend.entities.student.dto.SimpleStudentDTO;
import de.muenchen.oss.refarch.backend.entities.student.dto.StudentDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;
    private final StudentMapper mapper;

    @PreAuthorize(Authorities.Student.GET_ALL)
    @GetMapping("/students")
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleStudentDTO> getAllStudents() {
        return mapper.toSimpleStudentDTO(service.getAllStudents());
    }

    @PreAuthorize(Authorities.Student.GET_ONE_BY_ID)
    @GetMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO getStudent(@PathVariable final int id) {
        return mapper.toDTO(service.getStudent(id));
    }

    @PreAuthorize(Authorities.Student.GET_SEARCH)
    @GetMapping("/studentByName")
    @ResponseStatus(HttpStatus.OK)
    public List<SimpleStudentDTO> getStudentByName(@RequestParam(required = false, defaultValue = "") final String vorname,
            @RequestParam(required = false, defaultValue = "") final String nachname) {
        return mapper.toSimpleStudentDTO(service.findStudentsByName(vorname, nachname));
    }

    @PreAuthorize(Authorities.Student.DELETE_BY_ID)
    @DeleteMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteStudent(@PathVariable final int id) {
        service.deleteStudentAndRelatedData(id);
    }

    @PreAuthorize(Authorities.Student.PUT_BY_ID)
    @PutMapping("/student/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentDTO updateStudent(@PathVariable final int id, @RequestBody @Valid final CreateUpdateStudentRequestDTO body) {
        return mapper.toDTO(service.updateStudent(id, body));
    }

    @PreAuthorize(Authorities.Student.POST)
    @PostMapping("/student")
    @ResponseStatus(HttpStatus.CREATED)
    public Integer createStudent(@RequestBody @Valid final CreateUpdateStudentRequestDTO dto) {
        return service.createStudent(mapper.toStudent(dto));
    }
}
