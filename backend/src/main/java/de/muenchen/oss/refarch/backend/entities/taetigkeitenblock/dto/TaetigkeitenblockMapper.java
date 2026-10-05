package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.dto;

import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.Taetigkeitenblock;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.TaetigkeitenblockID;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TaetigkeitenblockMapper {
    TaetigkeitenblockDTO toDTO(Taetigkeitenblock taetigkeitenblock);

    List<TaetigkeitenblockDTO> toDTOs(List<Taetigkeitenblock> taetigkeitenblock);

    @Mapping(target = "taetigkeitenblockID.studentId", source = "studentId")
    @Mapping(target = "taetigkeitenblockID.beginnZeit", source = "beginnZeit")
    @Mapping(target = "taetigkeitenblockID.endeZeit", source = "endeZeit")
    @Mapping(target = "taetigkeitenblockID.tag", source = "tag")
    @Mapping(target = "homeoffice", source = "homeoffice")
    Taetigkeitenblock toEntity(TaetigkeitenblockCreationDTO dto);

    TaetigkeitenblockIdDTO toDTO(TaetigkeitenblockID taetigkeitenblockID);

    TaetigkeitenblockID toEntity(LocalDate tag, LocalTime beginnZeit, LocalTime endeZeit, int studentId);
}
