package com.vstr.apisrvc.adapter.out.jpa;

import com.vstr.apisrvc.application.entity.MngrEntity;
import com.vstr.apisrvc.application.entity.SchulMngrMapngEntity;
import com.vstr.apisrvc.application.entity.SchulMngrMapngId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchulMngrMapngRepository extends JpaRepository<SchulMngrMapngEntity, SchulMngrMapngId> {
    List<SchulMngrMapngEntity> findByMngrNo(MngrEntity mngrEntity);
}