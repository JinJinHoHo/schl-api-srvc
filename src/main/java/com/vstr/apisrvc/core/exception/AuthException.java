package com.vstr.apisrvc.core.exception;

import com.vstr.apisrvc.core.code.HttpCode;

public class AuthException extends SrvcException {

    public AuthException(HttpCode code) {
        super(code);
    }

    public String getHttpCode() {
        return "401";
    }
}
