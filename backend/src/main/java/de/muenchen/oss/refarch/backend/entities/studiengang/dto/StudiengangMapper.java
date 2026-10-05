package de.muenchen.oss.refarch.backend.entities.studiengang.dto;

import de.muenchen.oss.refarch.backend.entities.studiengang.Studiengang;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface StudiengangMapper {

    StudiengangDTO toDTO(Studiengang studiengang);

    List<StudiengangDTO> toDTO(List<Studiengang> studiengang);

    @Mapping(target = "studiengangNr", ignore = true)
    Studiengang toEntity(StudiengangCreationDTO dto);
}
