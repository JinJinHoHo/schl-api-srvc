package com.vstr.apisrvc.core.security;

import lombok.Getter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

@Getter
public enum SrvcAuthority {

    MNGM("학교 관리자", "/mngm/**", MngmUserAuthenticationProvider::new, MngmUserAuthenticationToken::unauthenticated),
    VSTRS("방문자", "/vstrs/**", VstrsUserAuthenticationProvider::new, VstrsUserAuthenticationToken::unauthenticated),
    OPTR("운영자", "/optr/**", null, null),
    KIOSK("키오스크", "/kiosk/**", null, null),
    TEST("테스트", "/test/**", null, null),
    SIGN("Signature", "/sign/**", null, null);

    private final String name;

    private final String patterns;

    private final Execute<? extends AuthenticationProvider, UserDetailsService> providerExecute;

    private final ExecuteParam2<? extends UsernamePasswordAuthenticationToken, String, String> genTokenExecute;

    SrvcAuthority(String name, String patterns,
                  Execute<? extends AuthenticationProvider, UserDetailsService> providerExecute,
                  ExecuteParam2<? extends UsernamePasswordAuthenticationToken, String, String> genTokenExecute) {
        this.name = name;
        this.patterns = patterns;
        this.providerExecute = providerExecute;
        this.genTokenExecute = genTokenExecute;
    }

    /**
     * AuthenticationProvider 반환.
     *
     * @param userDetailsService
     * @return
     */
    public AuthenticationProvider getProviderExecute(UserDetailsService userDetailsService) {
        if (providerExecute == null || userDetailsService == null)
            throw new RuntimeException(this.name + "에 AuthenticationProvider 지원되지 않음.");

        return providerExecute.execute(userDetailsService);
    }

    /**
     * 인증용 토큰 발행
     *
     * @param id 사용자 ID
     * @param pw 사용자 패스워드
     * @return
     */
    public Authentication publishAuthenticationToken(String id, String pw) {
        if (genTokenExecute == null)
            throw new RuntimeException(this.name + "에 AuthenticationToken 지원되지 않음.");
        return genTokenExecute.execute(id, pw);
    }

}

@FunctionalInterface
interface Execute<T, S> {
    /**
     * Runs this operation.
     */
    T execute(S s);
}

@FunctionalInterface
interface ExecuteParam2<T, S1, S2> {
    /**
     * Runs this operation.
     */
    T execute(S1 p1, S2 p2);
}


