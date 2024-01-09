package com.vstr.apisrvc.core.cache;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class RedisCacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    @Around("@annotation(RedisCacheable)")
    public Object cacheableProcess(ProceedingJoinPoint joinPoint) throws Throwable {
        RedisCacheable redisCacheable = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RedisCacheable.class);

        String cacheKey = generateKey(redisCacheable.cacheName(), joinPoint, redisCacheable.hasClassAndMethodNamePrefix());
        if (redisTemplate.hasKey(cacheKey)) return redisTemplate.opsForValue().get(cacheKey);

        return putCache(cacheKey, joinPoint.proceed(), redisCacheable.expireSecond());
    }

    @Around("@annotation(RedisCacheEvict)")
    public Object cacheEvictProcess(ProceedingJoinPoint joinPoint) throws Throwable {

        RedisCacheEvict redisCacheEvict = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RedisCacheEvict.class);

        if (redisCacheEvict.clearAll()) {
            Set<String> keys = redisTemplate.keys(redisCacheEvict.cacheName() + "*");
            redisTemplate.delete(keys);
        } else {
            String cacheKey = generateKey(redisCacheEvict.cacheName(), joinPoint, redisCacheEvict.hasClassAndMethodNamePrefix());
            redisTemplate.delete(cacheKey);
        }

        return joinPoint.proceed();
    }

    @Around("@annotation(RedisCachePut)")
    public Object cachePutProcess(ProceedingJoinPoint joinPoint) throws Throwable {

        RedisCachePut redisCachePut = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(RedisCachePut.class);

        String cacheKey = generateKey(redisCachePut.cacheName(), joinPoint, redisCachePut.hasClassAndMethodNamePrefix());

        return putCache(cacheKey, joinPoint.proceed(), redisCachePut.expireSecond());
    }

    private Object putCache(String cacheKey, Object value, long cacheTTL) {
        if (cacheTTL < 0) {
            redisTemplate.opsForValue().set(cacheKey, value);
        } else {
            redisTemplate.opsForValue().set(cacheKey, value, cacheTTL, TimeUnit.SECONDS);
        }
        return value;
    }

    private String generateKey(
            String cacheName,
            ProceedingJoinPoint joinPoint,
            Boolean hasClassAndMethodNamePrefix
    ) {
        String generatedKey = StringUtils.arrayToCommaDelimitedString(joinPoint.getArgs());
        if (hasClassAndMethodNamePrefix) {
            String target = joinPoint.getTarget().getClass().getSimpleName();
            String method = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
            return "%s::%s.%s(%s)".formatted(cacheName, target, method, generatedKey);
        }
        return "%s::(%s)".formatted(cacheName, generatedKey);
    }
}
