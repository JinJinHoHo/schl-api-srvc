package com.vstr.apisrvc.core.exception;


import com.vstr.apisrvc.core.code.HttpCode;

/**
 * 비지니스 프로세서 예외
 * <p>비지니스 프로세서 예외 발생 처리시 사용.</p>
 */
public class BizException extends SrvcException {
    public BizException(HttpCode code) {
        super(code);
    }

    public BizException(HttpCode code, Throwable cause) {
        super(code, cause);
    }

}
