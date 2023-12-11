package com.vstr.apisrvc.core.response;

import com.vstr.apisrvc.core.code.HttpCode;
import lombok.Getter;

/**
 * 예외 발생시 에러 응답.
 */
@Getter
public class ErrorResponse extends Response {


    public ErrorResponse(HttpCode code) {
        super(code);
    }

    public ErrorResponse(HttpCode code, String message) {
        super(code, message);
    }
}
