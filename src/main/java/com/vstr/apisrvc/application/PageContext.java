package com.vstr.apisrvc.application;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.vstr.apisrvc.core.code.Code;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonDeserialize
@JsonSerialize
public record PageContext(Map<String, List<Code>> code) implements Serializable {

}
