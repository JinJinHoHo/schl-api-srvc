package com.vstr.apisrvc.core.exception;

import com.vstr.apisrvc.core.code.HttpCode;

public class AuthException extends SrvcException {

    public AuthException() {
        super(HttpCode.unauthorized);
    }

    public AuthException(String msg) {
        super(HttpCode.unauthorized, msg);
    }
}
