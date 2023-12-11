package com.vstr.apisrvc.core.exception;


import com.vstr.apisrvc.core.code.HttpCode;

public class BizException extends SrvcException {
    public BizException(HttpCode code) {
        super(code);
    }

    public BizException(HttpCode code, Throwable cause) {
        super(code, cause);
    }

}
