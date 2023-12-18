package com.vstr.apisrvc.core.http;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Log4j2
public class ReqContext {

    protected static ThreadLocal<Map<ContextKey, Object>> threadLocal = new ThreadLocal<>();

    protected static void init(ServletRequest request) {
        Map<ContextKey, Object> map = new HashMap<>();
        String requestId = null;

        if (request instanceof HttpServletRequest httpRequest) {
            map.put(ContextKey.REQUEST_PATH, httpRequest.getRequestURI());
            requestId = httpRequest.getHeader(ContextKey.REQUEST_ID.toString());
        }

        if (requestId == null) requestId = UUID.randomUUID().toString().substring(0, 8);

        map.put(ContextKey.REQUEST_ID, requestId);
        MDC.put(ContextKey.REQUEST_ID.toString(), requestId);
        threadLocal.set(map);
    }


    /**
     * 로그인되여 있는 사용자 정보 UserDetails 정보 반환.
     *
     * @return
     */
    public static UserDetails getUserDetails() {
        return (UserDetails) threadLocal.get().get(ContextKey.USER_DETAILS);
    }

    public static void setUserDetails(UserDetails userDetails) {
        threadLocal.get().put(ContextKey.USER_DETAILS, userDetails);
    }


    public static String getRequestId() {
        return (String) threadLocal.get().get(ContextKey.REQUEST_ID);
    }

    public static String getRequestPath() {
        return (String) threadLocal.get().get(ContextKey.REQUEST_PATH);
    }


    protected static void clean() {
        threadLocal.remove();
        MDC.remove(ContextKey.REQUEST_ID.toString());
    }

    public static void lifecycle(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain,
            ContextLifecycle lifecycle) throws ServletException, IOException {

        init(request);

        try {

            lifecycle.process((HttpServletRequest) request);

            chain.doFilter(request, response);

        } catch (ServletException | IOException e) {
            log.trace(e);
            throw e;
        } finally {
            clean();
        }
    }

    public enum ContextKey {

        JWT_TOKEN,
        USER_DETAILS,

        JWT_NEW_TOKEN,
        REQUEST_ID,
        REQUEST_PATH
    }
}
