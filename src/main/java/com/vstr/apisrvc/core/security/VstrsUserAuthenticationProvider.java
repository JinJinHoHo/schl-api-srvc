package com.vstr.apisrvc.core.security;

import com.vstr.apisrvc.core.exception.AuthException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class VstrsUserAuthenticationProvider implements AuthenticationProvider {

    final UserDetailsService userDetailsService;


    public VstrsUserAuthenticationProvider(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user = userDetailsService.loadUserByUsername(username);
        if (user == null
            || !SpringSecurityConfig.passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException("사용자 정보가 존재 하지 않거나, 패스워드가 틀렸습니다.");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
        authenticationToken.setDetails(user);

        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return VstrsUserAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
