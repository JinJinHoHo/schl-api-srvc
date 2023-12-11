package com.vstr.apisrvc.core.code;

public enum HttpCode implements Code {
    success, error, not_found, vaild_error, error_response;


    public String getMsg() {
        return success.toString();
    }

    ;
}
