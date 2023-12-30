package com.vstr.apisrvc.adapter.out.jpa;

import com.vstr.apisrvc.application.entity.MngrEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.validation.constraints.Null;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MngrPageRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Long countByKeyword(String q) {
        String queryString = """
                select count(*)
                from MngrEntity ce
                where 1=1
                """;
        return orderAndParam(Long.class, queryString, q).getSingleResult();
    }

    public List<MngrEntity> findByKeyword(
            @Null String q,
            final Pageable pageable) {
        String queryString = """
                select m
                from MngrEntity m
                where 1=1
                """;
        return orderAndParam(MngrEntity.class, queryString, q)
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }


    protected <T> TypedQuery<T> orderAndParam(Class<T> resultClass, String queryString, String q) {

        if (StringUtils.isNotBlank(q)) queryString += " AND m.comNm like %:comNm%";

        queryString = (queryString + " ORDER BY m.mngrNm ASC");

        TypedQuery<T> query = entityManager.createQuery(queryString, resultClass);

        if (StringUtils.isNotBlank(q)) query.setParameter("comNm", q);

        return query;
    }
}
