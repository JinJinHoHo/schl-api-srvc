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
@Table(name = "schul", schema = "schul")
public class SchulEntity {
    @Id
    @Column(name = "schul_no", nullable = false)
    @Comment("학교번호")
    private Integer id;

    @Size(max = 50)
    @NotNull
    @Column(name = "schul_nm", nullable = false, length = 50)
    @Comment("학교명")
    private String schulNm;

    @Embedded
    private MnangeAudit mnangeAudit;

}