package de.muenchen.oss.refarch.backend.common;

import jakarta.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public abstract class BaseEntityCustomKey implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
