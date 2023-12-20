package com.vstr.apisrvc.application.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Comment;

/**
 * 키오스크
 */
@Getter
@Setter
@Entity
@Table(name = "kiosk", schema = "schul")
public class KioskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kiosk_no", nullable = false)
    @Comment("키오스크번호")
    private Integer id;

    @Size(max = 300)
    @NotNull
    @Column(name = "kiosk_manage_no", nullable = false, length = 300)
    @Comment("키오스크관리번호")
    private String kioskManageNo;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "schul_no", nullable = false)
    @Comment("학교번호")
    private SchulEntity schulNo;

    @Embedded
    private MnangeAudit mnangeAudit;

}