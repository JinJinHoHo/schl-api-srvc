package com.vstr.apisrvc.application.mngm;

import com.vstr.apisrvc.adapter.out.jpa.SchulVisitrRepository;
import com.vstr.apisrvc.application.PageContext;
import com.vstr.apisrvc.application.code.SexDstnCode;
import com.vstr.apisrvc.application.entity.SchulVisitrEntity;
import com.vstr.apisrvc.core.cache.RedisCacheable;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SchulVisitrService {

    private final SchulVisitrRepository schulVisitrRepository;

    public List<SchulVisitrEntity> list(Integer schulNo) {
        return schulVisitrRepository.findBySchulNo_Id(schulNo);
    }

    @RedisCacheable(cacheName = "PageContext::SchulVisitr", expireSecond = 10)
    public PageContext getPageContext() {
        return new PageContext(
                Map.of(
                        "sexDstn", List.of(SexDstnCode.values())
                ));
    }

}
