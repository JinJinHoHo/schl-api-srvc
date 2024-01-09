package com.vstr.apisrvc.core.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface RedisCacheEvict {
    /**
     * redis에 사용할 캐시 이름
     */
    String cacheName();

    /**
     * 캐시 key 생성에 class, method 이름을 사용할 것 인지 default : false
     * classAndMethodNamePrefix = true -> key::ClassName.MethodName(args...)
     * classAndMethodNamePrefix = false -> key::(args...)
     */
    boolean clearAll() default false;

    boolean hasClassAndMethodNamePrefix() default false;
}
