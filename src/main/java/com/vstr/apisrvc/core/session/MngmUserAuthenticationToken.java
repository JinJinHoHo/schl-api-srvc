package com.vstr.apisrvc.core.session;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class MngmUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private MngmUserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public static MngmUserAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new MngmUserAuthenticationToken(principal, credentials);
    }
}
