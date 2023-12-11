package com.vstr.apisrvc.core.response;

import com.vstr.apisrvc.core.ReqContext;
import com.vstr.apisrvc.core.code.HttpCode;
import lombok.Getter;

import java.util.Date;

@Getter
public class Response {

    private final HttpCode code;

    private String message;

    private final Date timestamp;

    private final String requestId;

    public Response(HttpCode code) {
        timestamp = new Date();
        requestId = "";

//        requestId = ReqContext.getRequestId();
        this.code = code;
        this.message = code.getMsg();
    }


    public Response(HttpCode code, String message) {
        this(code);
        this.message = message;
    }
}
