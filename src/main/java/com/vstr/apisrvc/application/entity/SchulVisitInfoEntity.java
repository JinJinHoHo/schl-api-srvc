package com.vstr.apisrvc.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "schul_visit_info", schema = "schul")
public class SchulVisitInfoEntity {
    @Id
    @Column(name = "visit_info_no", nullable = false)
    @Comment("방문 정보 번호")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visitr_no", nullable = false)
    @Comment("방문자 번호")
    private SchulVisitrEntity visitrNo;

    @NotNull
    @Column(name = "visit_de", nullable = false)
    @Comment("방문 일자")
    private LocalDate visitDe;

    @NotNull
    @Column(name = "visit_purps", nullable = false, length = Integer.MAX_VALUE)
    @Comment("방문 목적")
    private String visitPurps;

    @Size(max = 30)
    @NotNull
    @Column(name = "visit_sttus_code", nullable = false, length = 30)
    @Comment("방문 상태 코드")
    private String visitSttusCode;

    @Column(name = "entnc_dt")
    @Comment("입장 일시")
    private Instant entncDt;

    @Column(name = "exit_dt")
    @Comment("퇴장 일시")
    private Instant exitDt;

    @Embedded
    private VisitAudit visitAudit;

    @Embedded
    private MnangeAudit mnangeAudit;

    @Column(name = "sys_updt_dt")
    @Comment("방문 정보 번호")
    private Instant sysUpdtDt;

}