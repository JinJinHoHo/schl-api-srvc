package com.vstr.apisrvc.core.security;

import lombok.Getter;

@Getter
public enum SrvcAuthority {
    MNGM("학교 관리자", "/mngm/**"),
    VSTRS("방문자","/vstrs/**"),
    OPTR("운영자","/optr/**"),
    KIOSK("키오스크","/kiosk/**"),
    TEST("테스트","/test/**"),
    SIGN("Signature","/sign/**");

    private final String name;

    private final String[] patterns;

    SrvcAuthority(String name, String... patterns) {
        this.name = name;
        this.patterns = patterns;
    }

}
