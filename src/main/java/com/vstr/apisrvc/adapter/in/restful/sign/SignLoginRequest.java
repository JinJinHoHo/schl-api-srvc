package com.vstr.apisrvc.adapter.in.restful.sign;

import com.vstr.apisrvc.core.security.SrvcAuthority;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Getter
@Setter
@Schema(title = "로그인 요청")
public class SignLoginRequest {

    @NotBlank
    @Schema(description = "서비스 권한", requiredMode = REQUIRED, defaultValue = "MNGM")
    private SrvcAuthority srvc;

    @NotBlank
    @Schema(description = "사용자 ID", requiredMode = REQUIRED, defaultValue = "user")
    private String usrId;

    @NotBlank
    @Schema(description = "사용자 패스워드", requiredMode = REQUIRED, defaultValue = "password")
    private String pswd;
}
