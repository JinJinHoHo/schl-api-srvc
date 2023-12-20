package com.vstr.apisrvc.application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SchulMngrMapngId implements Serializable {

    @Serial
    private static final long serialVersionUID = 4267362766091628536L;
    @NotNull
    @Column(name = "mngr_no", nullable = false)
    private Integer mngrNo;

    @NotNull
    @Column(name = "schul_no", nullable = false)
    private Integer schulNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SchulMngrMapngId entity = (SchulMngrMapngId) o;
        return Objects.equals(this.mngrNo, entity.mngrNo) &&
               Objects.equals(this.schulNo, entity.schulNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mngrNo, schulNo);
    }

}