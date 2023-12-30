package com.vstr.apisrvc.core.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vstr.apisrvc.core.http.ReqResFilter;
import com.vstr.apisrvc.core.security.HeaderAndCookieSessionIdResolver;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.security.jackson2.SecurityJackson2Modules;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
@EnableRedisHttpSession
public class SessionConfig implements BeanClassLoaderAware {

    private ClassLoader loader;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory();
    }


    @Bean
    public RedisSerializer<Object> springSessionDefaultRedisSerializer() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(SecurityJackson2Modules.getModules(this.loader));
//        mapper.registerModule(new SimpleModule().setMixInAnnotation());
        mapper.registerSubtypes(MngmUserAuthenticationToken.class, VstrsUserAuthenticationToken.class);
        return new GenericJackson2JsonRedisSerializer(mapper);
    }


    /*
     * @see
     * org.springframework.beans.factory.BeanClassLoaderAware#setBeanClassLoader(java.lang
     * .ClassLoader)
     */
    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.loader = classLoader;
    }

    @Bean
    public HttpSessionIdResolver sessionIdResolver() {
        return new HeaderAndCookieSessionIdResolver();
    }

    @Bean
    public FilterRegistrationBean<ReqResFilter> firstFilterRegister() {
        return new FilterRegistrationBean<>(new ReqResFilter());
    }
}
