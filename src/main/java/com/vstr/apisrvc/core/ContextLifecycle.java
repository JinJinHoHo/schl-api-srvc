package com.vstr.apisrvc.core;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public interface ContextLifecycle {
    void process(HttpServletRequest servletRequest) throws ServletException, IOException;
}
