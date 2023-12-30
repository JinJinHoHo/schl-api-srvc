package com.vstr.apisrvc.core.session;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface SrvcUserDetailsService extends UserDetailsService {

    UserSession getUserSession(String id);
}
