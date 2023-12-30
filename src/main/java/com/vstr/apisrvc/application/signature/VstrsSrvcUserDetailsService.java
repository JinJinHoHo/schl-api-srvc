package com.vstr.apisrvc.application.signature;

import com.vstr.apisrvc.core.session.UserSession;
import com.vstr.apisrvc.core.security.SpringSecurityConfig;
import com.vstr.apisrvc.core.session.SrvcAuthority;
import com.vstr.apisrvc.core.session.SrvcUserDetailsService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 방문자 UserDetailsService
 */
@Log4j2
@Service
public class VstrsSrvcUserDetailsService implements SrvcUserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info(username);
        return User.withUsername("user")
                .passwordEncoder(SpringSecurityConfig.passwordEncoder::encode).password("password")
                .roles("USER")
                .authorities(SrvcAuthority.MNGM.name())
                .build();
    }

    @Override
    public UserSession getUserSession(String id) {
        return null;
    }
}
