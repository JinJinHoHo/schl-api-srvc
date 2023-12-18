package com.vstr.apisrvc.core.exception;


import com.vstr.apisrvc.core.code.HttpCode;
import lombok.Getter;

/**
 * 서비스 예외.
 * <p>공통 영역 에러 처리시 사용.</p>
 */
@Getter
public class SrvcException extends RuntimeException {

    HttpCode code;

    public SrvcException(HttpCode code) {
        super(code.getMsg());
        this.code = code;
    }

    public SrvcException(HttpCode code, String msg) {
        super(msg);
        this.code = code;
    }

    public SrvcException(HttpCode code, Throwable cause) {
        super(code.getMsg(), cause);
        this.code = code;
    }


    public int getHttpCode() {
        return code.getHttpCode();
    }
}
