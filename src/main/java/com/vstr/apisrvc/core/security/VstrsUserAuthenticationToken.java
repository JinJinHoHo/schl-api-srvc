package com.vstr.apisrvc.core.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class VstrsUserAuthenticationToken extends UsernamePasswordAuthenticationToken {

    public VstrsUserAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

//    public VstrsUserAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
//        super(principal, credentials, authorities);
//    }

    public static VstrsUserAuthenticationToken unauthenticated(Object principal, Object credentials) {
        return new VstrsUserAuthenticationToken(principal, credentials);
    }
}
