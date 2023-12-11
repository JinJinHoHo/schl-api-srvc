package com.vstr.apisrvc.core.response;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.response.Response;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<R> extends Response {

    final Integer pageNumber;

    final Integer pageSize;

    final Long totalContentCount;

    final Integer totalPageSize;

    private final List<R> pageList;

    public PageResponse(HttpCode code, Page<R> usrEntityPage) {
        super(code);
        pageNumber = usrEntityPage.getNumber();
        pageSize = usrEntityPage.getSize();
        totalContentCount = usrEntityPage.getTotalElements();
        totalPageSize = usrEntityPage.getTotalPages();
        pageList = usrEntityPage.getContent();

    }

}
