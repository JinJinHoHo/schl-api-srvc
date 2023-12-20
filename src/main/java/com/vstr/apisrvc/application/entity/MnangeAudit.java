package com.vstr.apisrvc.application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.time.Instant;

@Getter
@Setter
@Embeddable
public class MnangeAudit {

    @Column(name = "manage_creat_mngr_no")
    @Comment("관리 생성 관리자 번호")
    private Integer manageCreatMngrNo;

    @Column(name = "manage_creat_dt")
    @Comment("관리 생성 일시")
    private Instant manageCreatDt;

    @Column(name = "manage_updt_mngr_no")
    @Comment("관리 수정 관리자 번호")
    private Integer manageUpdtMngrNo;

    @Column(name = "manage_updt_dt")
    @Comment("관리 수정 일시")
    private Instant manageUpdtDt;
}