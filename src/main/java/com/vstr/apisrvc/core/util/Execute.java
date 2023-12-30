package com.vstr.apisrvc.core.util;

@FunctionalInterface
public interface Execute<T, S> {
    /**
     * Runs this operation.
     */
    T execute(S s);
}
