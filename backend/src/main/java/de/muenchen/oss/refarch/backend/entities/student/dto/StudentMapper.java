package de.muenchen.oss.refarch.backend.entities.student.dto;

import de.muenchen.oss.refarch.backend.entities.praktikum.dto.PraktikumMapper;
import de.muenchen.oss.refarch.backend.entities.student.Student;
import de.muenchen.oss.refarch.backend.entities.studiengang.dto.StudiengangMapper;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = { PraktikumMapper.class, StudiengangMapper.class })
public interface StudentMapper {

    @Mapping(target = "url", ignore = true)
    StudentDTO toDTO(Student student);

    @Mapping(target = "url", ignore = true)
    List<StudentDTO> toDTOs(List<Student> students);

    List<SimpleStudentDTO> toSimpleStudentDTO(List<Student> student);

    @Mapping(target = "studiengaenge", ignore = true)
    @Mapping(target = "praktikum", ignore = true)
    Student toStudent(SimpleStudentDTO simpleStudentDTO);

    @Mapping(target = "studiengaenge", ignore = true)
    @Mapping(target = "praktikum", ignore = true)
    @Mapping(target = "studentId", ignore = true)
    Student toStudent(CreateUpdateStudentRequestDTO dto);
}
