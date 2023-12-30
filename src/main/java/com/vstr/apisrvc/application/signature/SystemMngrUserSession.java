package com.vstr.apisrvc.application.signature;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.vstr.apisrvc.core.session.UserSession;

@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
public record SystemMngrUserSession(String id, String name) implements UserSession {
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }
}
