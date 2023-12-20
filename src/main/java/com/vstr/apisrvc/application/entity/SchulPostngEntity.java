package com.vstr.apisrvc.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

@Getter
@Setter
@Entity
@Table(name = "schul_postng", schema = "schul")
public class SchulPostngEntity {
    @Id
    @Column(name = "schul_postng_no", nullable = false)
    @Comment("학교 배치 번호")
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schul_no", nullable = false)
    @Comment("학교번호")
    private SchulEntity schulNo;

    @Size(max = 50)
    @NotNull
    @Column(name = "nm", nullable = false, length = 50)
    @Comment("배치 명")
    private String nm;

    @Embedded
    private MnangeAudit mnangeAudit;

}