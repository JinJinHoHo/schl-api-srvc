package com.vstr.apisrvc.core.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class MngmUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public MngmUserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

//    public MngmUserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
//        super(principal, credentials, authorities);
//    }

    public static MngmUserAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new MngmUserAuthenticationToken(principal, credentials);
    }
}
