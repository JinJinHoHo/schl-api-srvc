package com.vstr.apisrvc.adapter.out.jpa;

import com.vstr.apisrvc.application.entity.SchulVisitrEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchulVisitrRepository extends JpaRepository<SchulVisitrEntity, Integer> {

    List<SchulVisitrEntity> findBySchulNo_Id(Integer schulNo);
}