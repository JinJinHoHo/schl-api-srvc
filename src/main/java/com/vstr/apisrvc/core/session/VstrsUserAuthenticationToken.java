package com.vstr.apisrvc.core.session;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class VstrsUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private VstrsUserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public static VstrsUserAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new VstrsUserAuthenticationToken(principal, credentials);
    }
}
