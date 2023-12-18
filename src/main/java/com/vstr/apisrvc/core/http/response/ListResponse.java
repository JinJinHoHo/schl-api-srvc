package com.vstr.apisrvc.core.http.response;

import com.vstr.apisrvc.core.code.HttpCode;
import lombok.Getter;

import java.util.List;

@Getter
public class ListResponse<R> extends Response {

    private final List<R> list;

    public ListResponse(HttpCode code, List<R> list) {
        super(code);
        this.list = list;
    }
}
