package com.vstr.apisrvc.application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Comment;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class SchulVisitVhcleId implements Serializable {
    @Serial
    private static final long serialVersionUID = 5139802962414433626L;

    @Size(max = 15)
    @NotNull
    @Column(name = "visit_vhcle_no", nullable = false, length = 15)
    @Comment("방문 차량 번호")
    private String visitVhcleNo;

    @NotNull
    @Column(name = "visit_info_no", nullable = false)
    @Comment("방문 정보 번호")
    private Integer visitInfoNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(super.equals(o)) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SchulVisitVhcleId entity = (SchulVisitVhcleId) o;
        return Objects.equals(this.visitVhcleNo, entity.visitVhcleNo) &&
               Objects.equals(this.visitInfoNo, entity.visitInfoNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(visitVhcleNo, visitInfoNo);
    }

}