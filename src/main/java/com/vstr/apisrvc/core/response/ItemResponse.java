package com.vstr.apisrvc.core.response;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.exception.BizException;
import com.vstr.apisrvc.core.response.Response;
import lombok.Getter;

@Getter
public class ItemResponse<R> extends Response {

    private final R item;

    public ItemResponse(HttpCode code, R item) {
        super(code);

        if (item == null) throw new BizException(HttpCode.not_found);

        this.item = item;
    }
}
