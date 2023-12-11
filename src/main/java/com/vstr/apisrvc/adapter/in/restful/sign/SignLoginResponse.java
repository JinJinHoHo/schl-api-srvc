package com.vstr.apisrvc.adapter.in.restful.sign;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.response.Response;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 로그인 성공 응답 결과
 */
@Getter
@Schema(title = "로그인 응답")
public class SignLoginResponse extends Response {

    public SignLoginResponse() {
        super(HttpCode.success);
    }

}
