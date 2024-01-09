package com.vstr.apisrvc.core.cache;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class RedisCacheAspect {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 캐시 데이터가 있으면 캐시 데이터 반환, 없으면 캐시에 저장후 반환처리 됨.
     *
     * @param joinPoint ProceedingJoinPoint
     * @return 데이터.
     * @throws Throwable
     */
    @Around("@annotation(RedisCacheable)")
    public Object cacheableProcess(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        RedisCacheable redisCacheable = method.getAnnotation(RedisCacheable.class);

        String cacheKey = makeKey(redisCacheable.cacheName(), joinPoint, redisCacheable.hasClassAndMethodNamePrefix());

        if (Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey))) return redisTemplate.opsForValue().get(cacheKey);

        return putCache(cacheKey, joinPoint.proceed(), redisCacheable.expireSecond());
    }

    /**
     * 캐시 삭제 처리.
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(RedisCacheEvict)")
    public Object cacheEvictProcess(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        RedisCacheEvict redisCacheEvict = method.getAnnotation(RedisCacheEvict.class);

        Collection<String> keys;
        if (redisCacheEvict.clearAll()) {
            keys = redisTemplate.keys(redisCacheEvict.cacheName() + "*");
        } else {
            keys = List.of(makeKey(redisCacheEvict.cacheName(), joinPoint, redisCacheEvict.hasClassAndMethodNamePrefix()));
        }
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }

        return joinPoint.proceed();
    }

    /**
     * 캐시 등록. 기존 데이터 확인 없이 캐시에 저장처리.
     *
     * @param joinPoint ProceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("@annotation(RedisCachePut)")
    public Object cachePutProcess(ProceedingJoinPoint joinPoint) throws Throwable {

        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();

        RedisCachePut redisCachePut = method.getAnnotation(RedisCachePut.class);

        String cacheKey = makeKey(redisCachePut.cacheName(), joinPoint, redisCachePut.hasClassAndMethodNamePrefix());

        return putCache(cacheKey, joinPoint.proceed(), redisCachePut.expireSecond());
    }

    /**
     * @param cacheKey 캐시키.
     * @param value    저장할 캐시 데이터
     * @param ttl 캐시 만료 시간(초) 1 보다 작은 수 입력시 만료시간 설정 안됨.,
     * @return
     */
    private Object putCache(String cacheKey, Object value, long ttl) {
        if (ttl >= 1) {
            redisTemplate.opsForValue().set(cacheKey, value, ttl, TimeUnit.SECONDS);
        } else {
            redisTemplate.opsForValue().set(cacheKey, value);
        }
        return value;
    }

    private String makeKey(
            String cacheName,
            ProceedingJoinPoint joinPoint,
            Boolean hasClassAndMethodNamePrefix
    ) {
        //메소드 파라미터 문자열 생성.
        String k = StringUtils.arrayToCommaDelimitedString(joinPoint.getArgs());

        //캐시명과 파라미터값 으로만 캐시키 생성.
        if (!hasClassAndMethodNamePrefix) return "%s::(%s)".formatted(cacheName, k);

        //캐시명, 호출클래스,메소드명, 파라미터값을 조합하여 캐시키 생성.
        String target = joinPoint.getTarget().getClass().getSimpleName();
        String method = ((MethodSignature) joinPoint.getSignature()).getMethod().getName();
        return "%s::%s.%s(%s)".formatted(cacheName, target, method, k);
    }
}
