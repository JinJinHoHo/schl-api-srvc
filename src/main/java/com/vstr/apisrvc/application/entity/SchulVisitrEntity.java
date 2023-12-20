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
@Table(name = "schul_visitr", schema = "schul")
public class SchulVisitrEntity {

    @Id
    @Column(name = "visitr_no", nullable = false)
    @Comment("방문자번호")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schul_no", nullable = false)
    @Comment("학교 번호")
    private SchulEntity schulNo;

    @Size(max = 300)
    @NotNull
    @Column(name = "visitr_idntfr", nullable = false, length = 300)
    @Comment("방문자식별자")
    private String visitrIdntfr;

    @Size(max = 50)
    @NotNull
    @Column(name = "nm", nullable = false, length = 50)
    @Comment("이름")
    private String nm;

    @Size(max = 50)
    @Column(name = "psitn", length = 50)
    @Comment("소속")
    private String psitn;

    @Size(max = 50)
    @Column(name = "ofcps", length = 50)
    @Comment("직위")
    private String ofcps;

    @Size(max = 30)
    @NotNull
    @Column(name = "cttpc", nullable = false, length = 30)
    @Comment("연락처")
    private String cttpc;

    @Size(max = 30)
    @NotNull
    @Column(name = "sexdstn", nullable = false, length = 30)
    @Comment("성별")
    private String sexdstn;

    @NotNull
    @Column(name = "last_login_dt", nullable = false)
    @Comment("마지막 로그인 일시")
    private Instant lastLoginDt;

    @Embedded
    private VisitAudit visitAudit;

    @Embedded
    private MnangeAudit mnangeAudit;

}