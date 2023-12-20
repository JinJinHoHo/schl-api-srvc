package com.vstr.apisrvc.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "schul_visit_vhcle", schema = "schul")
public class SchulVisitVhcleEntity {
    @EmbeddedId
    private SchulVisitVhcleId id;

    @MapsId("visitInfoNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visit_info_no", nullable = false)
    private SchulVisitInfoEntity visitInfoNo;

    @Size(max = 50)
    @NotNull
    @Column(name = "vhcty", nullable = false, length = 50)
    @Comment("차종")
    private String vhcty;

    @Embedded
    private VisitAudit visitAudit;

    @Embedded
    private MnangeAudit mnangeAudit;
}