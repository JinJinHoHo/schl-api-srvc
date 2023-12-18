package com.vstr.apisrvc.core.exception;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.http.response.ErrorResponse;
import lombok.Getter;

import java.util.Map;

@Getter
public class VaildErrorResponse extends ErrorResponse {

    private final Map<String, String> bindErrors;

    public VaildErrorResponse(HttpCode code, String message, Map<String, String> bindErrors) {
        super(code, message);
        this.bindErrors = bindErrors;
    }
}
