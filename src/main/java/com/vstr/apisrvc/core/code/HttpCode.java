package com.vstr.apisrvc.core.code;

import lombok.Getter;

@Getter
public enum HttpCode implements Code {
    success(200), error(500), not_found(404), vaild_error(400), error_response(500), unauthorized(401), forbidden(403);


    HttpCode(Integer httpCode) {
        this.httpCode = httpCode;
    }

    final Integer httpCode;

    public String getLabel() {
        return success.toString();
    }

}
