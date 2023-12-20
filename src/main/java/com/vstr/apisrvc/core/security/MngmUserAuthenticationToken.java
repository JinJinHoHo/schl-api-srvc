package com.vstr.apisrvc.core.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MngmUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    private MngmUserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public static MngmUserAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new MngmUserAuthenticationToken(principal, credentials);
    }
}
