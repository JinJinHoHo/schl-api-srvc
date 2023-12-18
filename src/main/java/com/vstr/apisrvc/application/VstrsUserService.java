package com.vstr.apisrvc.application;

import com.vstr.apisrvc.core.security.SpringSecurityConfig;
import com.vstr.apisrvc.core.security.SrvcAuthority;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class VstrsUserService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        return User.withUsername("user")
                .passwordEncoder(SpringSecurityConfig.passwordEncoder::encode).password("password")
                .roles("USER")
                .authorities(SrvcAuthority.MNGM.name())
                .build();
    }
}
