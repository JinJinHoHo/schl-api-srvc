package com.vstr.apisrvc.core.exception;


import com.vstr.apisrvc.core.code.HttpCode;
import lombok.Getter;

@Getter
public class SrvcException extends RuntimeException {

    HttpCode code;

    public SrvcException(HttpCode code) {
        super(code.getMsg());
        this.code = code;
    }

    public SrvcException(HttpCode code, Throwable cause) {
        super(code.getMsg(), cause);
        this.code = code;
    }

}
