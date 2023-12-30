package com.vstr.apisrvc.application.code;

import com.vstr.apisrvc.core.code.Code;

/**
 * 관리자 유형 코드
 */
public enum MngrTyCode implements Code {
    SYSTEM("시스템"),
    SCHUL("학교");

    final String label;

    MngrTyCode(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return null;
    }
}
