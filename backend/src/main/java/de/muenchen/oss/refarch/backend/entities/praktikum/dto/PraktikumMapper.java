package de.muenchen.oss.refarch.backend.entities.praktikum.dto;

import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.student.StudentRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public abstract class PraktikumMapper {

    @Autowired
    protected StudentRepository studentRepository;

    public PraktikumDTO toDTO(final Praktikum praktikum) {
        if (praktikum == null) {
            return null;
        }
        return new PraktikumDTO(praktikum.getBeginnDatum(), praktikum.getEndeDatum(), praktikum.getStudentId(), praktikum.getWochenarbeitszeit(),
                praktikum.getBenoetigteWochen());
    }

    @Mapping(target = "studentId", source = "student.studentId")
    public abstract ReducedPraktikumDTO toPraktikumStudentDTO(Praktikum praktikum);

    @Mapping(
            target = "student",
            expression = "java(studentRepository.findById(dto.studentId()).orElseThrow(()-> new de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.NotFoundException(de.muenchen.oss.refarch.backend.common.exceptionhandling.exceptions.ExceptionMessageConstants.STUDENT_NOT_FOUND)))"
    )
    public abstract Praktikum toPraktikum(PraktikumDTO dto);

    @Mapping(target = "studentId", ignore = true)
    @Mapping(target = "student", ignore = true)
    public abstract Praktikum toPraktikum(PraktikumUpdateDTO dto);

    @Mapping(target = "taetigkeiten", source = "taetigkeiten")
    @Mapping(target = "zeitgutschriften", source = "zeitgutschriften")
    public abstract FullPraktikumDTO toFullPraktikumDTO(FullPraktikumAggregat aggregat);
}
