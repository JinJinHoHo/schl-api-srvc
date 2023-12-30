package com.vstr.apisrvc.application.code;

import com.vstr.apisrvc.core.code.Code;

public enum SexDstnCode implements Code {
    MALE("남자"),

    FEMALE("여자");


    final String label;

    SexDstnCode(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return null;
    }
}
