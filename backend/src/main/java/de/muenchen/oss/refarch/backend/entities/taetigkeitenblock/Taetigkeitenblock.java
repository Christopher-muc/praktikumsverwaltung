package de.muenchen.oss.refarch.backend.entities.taetigkeitenblock;

import de.muenchen.oss.refarch.backend.common.BaseEntityCustomKey;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
public class Taetigkeitenblock extends BaseEntityCustomKey {
    @EmbeddedId
    private TaetigkeitenblockID taetigkeitenblockID;

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    @NotNull private boolean homeoffice;
}
