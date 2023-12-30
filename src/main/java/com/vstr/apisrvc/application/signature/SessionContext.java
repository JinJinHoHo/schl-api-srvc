package com.vstr.apisrvc.application.signature;

import com.vstr.apisrvc.core.exception.AuthException;
import com.vstr.apisrvc.core.session.UserSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionContext {
    /**
     * SecurityContext에 세션 정보가 있으면 세션 정보 반환.
     *
     * @param sessionType 반환 받고 하는 세션 타임.SecurityContext에 적재된 타입이 다르면 권한 오류 발생됨.
     * @param <T>
     * @return
     */
    public static <T extends UserSession> T getSession(Class<T> sessionType) {

        if (sessionType == null) throw new IllegalArgumentException("sessionType cannot be null");

        Authentication authentication = getAuthentication();
        Object details = authentication.getDetails();

        if (!sessionType.isInstance(details)) throw new AuthException("사용 권한 없음.");

        return sessionType.cast(details);
    }

    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
