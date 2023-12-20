package com.vstr.apisrvc.application.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "schul_mngr_mapng", schema = "schul")
public class SchulMngrMapngEntity {
    @EmbeddedId
    private SchulMngrMapngId id;

    @MapsId("mngrNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mngr_no", nullable = false)
    @Comment("관리자 번호")
    private MngrEntity mngrNo;

    @MapsId("schulNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schul_no", nullable = false)
    @Comment("학교 번호")
    private SchulEntity schulNo;

}