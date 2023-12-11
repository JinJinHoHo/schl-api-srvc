package com.vstr.apisrvc.core.response;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.response.ErrorResponse;
import lombok.Getter;

@Getter
public class ExceptionErrorResponse extends ErrorResponse {

    private final String exception;
    private final String message;

    public ExceptionErrorResponse(HttpCode code, String exception, String message) {
        super(code);
        this.exception = exception;
        this.message = message;
    }
}
