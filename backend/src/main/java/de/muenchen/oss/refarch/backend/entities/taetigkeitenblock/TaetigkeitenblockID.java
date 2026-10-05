package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaetigkeitenblockID implements Serializable {
    private int studentId;
    private LocalTime beginnZeit;
    private LocalTime endeZeit;
    private LocalDate tag;

    private static final long serialVersionUID = 1L;
}
