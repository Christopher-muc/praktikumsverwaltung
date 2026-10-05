package de.muenchen.oss.refarch.backend.entities.zeitgutschrift.dto;

import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.Zeitgutschrift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class ZeitgutschriftMapper {

    @Mapping(target = "id", ignore = true)
    public abstract SimpleZeitgutschriftDTO toDTO(Zeitgutschrift zeitgutschrift);
}
