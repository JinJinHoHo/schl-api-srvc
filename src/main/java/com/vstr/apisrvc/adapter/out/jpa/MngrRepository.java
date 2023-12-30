package com.vstr.apisrvc.adapter.out.jpa;

import com.vstr.apisrvc.application.code.MngrTyCode;
import com.vstr.apisrvc.application.entity.MngrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
public interface MngrRepository extends JpaRepository<MngrEntity, Integer> {

    MngrEntity findByMngrIdAndMngrTyCode(String mngrId, MngrTyCode mngrTyCode);

    @Modifying
    @Query("""
            update MngrEntity m set m.lastLoginDt=:lastLoginDt where m=:mngr
            """)
    void updateLastLoginDt(MngrEntity mngr, Instant lastLoginDt);
}
