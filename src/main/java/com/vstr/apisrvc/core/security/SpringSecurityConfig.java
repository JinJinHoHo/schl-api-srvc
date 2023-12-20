package com.vstr.apisrvc.core.security;


import com.vstr.apisrvc.application.MngmUserService;
import com.vstr.apisrvc.application.VstrsUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;

import java.util.List;


@Configuration
@EnableWebSecurity
@Log4j2
@RequiredArgsConstructor
public class SpringSecurityConfig {

    public static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    final MngmUserService mngmUserService;
    final VstrsUserService vstrsUserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .httpBasic(HttpBasicConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.OPTIONS).permitAll()
                        .requestMatchers("/swagger-ui/**", "/api-docs/**", "/v3/**").permitAll()
                        .requestMatchers("/test/**").permitAll()
                        .requestMatchers("/sign/**").permitAll()
                        .requestMatchers("/kiosk/**").permitAll()
                        .requestMatchers("/mngm/**").hasAuthority(SrvcAuthority.MNGM.name())
                        .requestMatchers("/vstrs/**").hasAuthority(SrvcAuthority.VSTRS.name())
                        .requestMatchers("/optr/**").hasAuthority(SrvcAuthority.OPTR.name())
                        .anyRequest().authenticated()
                )
                .sessionManagement((sessions) -> sessions
                        .sessionConcurrency((concurrency) -> concurrency
                                .maximumSessions(1)
                                .maxSessionsPreventsLogin(true)
                        )
                );
        return http.build();
    }

    @Bean
    public DelegatingSecurityContextRepository delegatingSecurityContextRepository() {
        return new DelegatingSecurityContextRepository(
                new RequestAttributeSecurityContextRepository(),
                new HttpSessionSecurityContextRepository()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(List.of(
                SrvcAuthority.MNGM.getProviderExecute(mngmUserService),
                SrvcAuthority.VSTRS.getProviderExecute(vstrsUserService)
        ));
    }
}
