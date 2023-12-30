package com.vstr.apisrvc.application.entity;

import com.vstr.apisrvc.application.code.MngrTyCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "mngr", schema = "schul", indexes = {
        @Index(name = "idx_mngr_unq_01", columnList = "mngr_id", unique = true)
})
public class MngrEntity {
    @Id
    @Column(name = "mngr_no", nullable = false)
    @Comment("관리자번호")
    private Integer id;

    @Size(max = 30)
    @NotNull
    @Column(name = "mngr_ty_code", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    @Comment("관리자타입코드")
    private MngrTyCode mngrTyCode;

    @Size(max = 30)
    @NotNull
    @Column(name = "mngr_id", nullable = false, length = 30)
    @Comment("관리자ID")
    private String mngrId;

    @Size(max = 50)
    @NotNull
    @Column(name = "mngr_nm", nullable = false, length = 50)
    @Comment("관리자이름")
    private String mngrNm;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    @Comment("패스워드")
    private String password;

    @Size(max = 30)
    @NotNull
    @Column(name = "ct_actvty_at", nullable = false, length = 30)
    @ColumnDefault("false")
    @Comment("비활성 여부")
    private boolean ctActvtyAt = false;

    @NotNull
    @Column(name = "last_login_dt")
    @Comment("마지막 로그인 일자")
    private Instant lastLoginDt;

    @Embedded
    private MnangeAudit mnangeAudit;

}