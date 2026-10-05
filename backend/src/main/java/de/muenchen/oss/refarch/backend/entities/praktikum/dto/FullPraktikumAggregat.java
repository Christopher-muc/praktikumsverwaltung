package de.muenchen.oss.refarch.backend.entities.praktikum.dto;

import de.muenchen.oss.refarch.backend.entities.praktikum.Praktikum;
import de.muenchen.oss.refarch.backend.entities.taetigkeitenblock.Taetigkeitenblock;
import de.muenchen.oss.refarch.backend.entities.zeitgutschrift.Zeitgutschrift;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class FullPraktikumAggregat {
    private final List<Taetigkeitenblock> taetigkeiten;
    private final List<Zeitgutschrift> zeitgutschriften;
    private final LocalDate beginnDatum;
    private final LocalDate endeDatum;
    private final int studentId;
    private final int wochenarbeitszeit;
    private final int benoetigteWochen;

    public FullPraktikumAggregat(final Praktikum praktikum, final List<Taetigkeitenblock> taetigkeiten, final List<Zeitgutschrift> zeitgutschriftList) {
        benoetigteWochen = praktikum.getBenoetigteWochen();
        wochenarbeitszeit = praktikum.getWochenarbeitszeit();
        beginnDatum = praktikum.getBeginnDatum();
        endeDatum = praktikum.getEndeDatum();
        studentId = praktikum.getStudentId();
        this.taetigkeiten = taetigkeiten;
        this.zeitgutschriften = zeitgutschriftList;
    }
}
