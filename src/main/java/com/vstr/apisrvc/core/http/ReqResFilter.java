package com.vstr.apisrvc.core.http;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

@Log4j2
public class ReqResFilter implements Filter {

    public static final String REQUEST_ID = "request_id";

    @Override
    public void doFilter(
            ServletRequest request, ServletResponse response, FilterChain chain
    ) throws IOException, ServletException {

        String requestId = null;

        if (request instanceof HttpServletRequest httpRequest) {
            requestId = httpRequest.getHeader(REQUEST_ID);
        }

        if (requestId == null) requestId = UUID.randomUUID().toString().substring(0, 8);

        request.setAttribute(REQUEST_ID,requestId);
        MDC.put(REQUEST_ID, requestId);
        chain.doFilter(request, response);
    }
}
