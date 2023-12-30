package com.vstr.apisrvc.core.util;

@FunctionalInterface
public interface ExecuteParam2<T, S1, S2> {
    /**
     * Runs this operation.
     */
    T execute(S1 p1, S2 p2);
}
