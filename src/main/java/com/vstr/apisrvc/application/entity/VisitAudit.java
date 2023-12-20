package com.vstr.apisrvc.application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

@Getter
@Setter
@Embeddable
public class VisitAudit {


    @NotNull
    @Column(name = "visit_creat_dt", nullable = false)
    @Comment("방문 생성 일자")
    private Instant visitCreatDt;

    @NotNull
    @Column(name = "visit_updde_city", nullable = false)
    @Comment("방문 수정 일자")
    private Instant visitUpddeCity;
}