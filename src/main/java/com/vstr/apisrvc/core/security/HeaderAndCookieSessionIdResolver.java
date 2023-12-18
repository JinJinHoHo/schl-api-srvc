package com.vstr.apisrvc.core.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

import java.util.List;

import static org.springframework.session.web.http.CookieSerializer.*;

/**
 * 세션 생성 로직에서 ID 발급시 헤더와 쿠키에 세션 ID값 설정됨.
 * 세션ID 읽어 드릴때는 해더에 있는 값을 우선으로 읽어 드리고, 값이 없을시에 만 쿠키값 사용.
 */
public class HeaderAndCookieSessionIdResolver extends HeaderHttpSessionIdResolver {

    /**
     * 해더 또는 쿠키에 세션 ID 키.
     */
    public static final String HEADER_X_AUTH_TOKEN = "X-Auth-Token";

    private final CookieSerializer cookieSerializer;

    public HeaderAndCookieSessionIdResolver() {

        super(HEADER_X_AUTH_TOKEN);

        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName(HeaderAndCookieSessionIdResolver.HEADER_X_AUTH_TOKEN);
        serializer.setCookiePath("/");
        serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$");
        this.cookieSerializer = serializer;

    }

    @Override
    public List<String> resolveSessionIds(HttpServletRequest request) {
        List<String> list = super.resolveSessionIds(request);

        if (!list.isEmpty()) return list;

        return cookieSerializer.readCookieValues(request);
    }

    @Override
    public void setSessionId(HttpServletRequest request, HttpServletResponse response, String sessionId) {
        super.setSessionId(request, response, sessionId);
        cookieSerializer.writeCookieValue(new CookieValue(request, response, sessionId));
    }
}