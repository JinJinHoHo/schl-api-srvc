package com.vstr.apisrvc.core.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(CsrfConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
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
//                .securityContext((securityContext) ->
//                        securityContext
//                                .securityContextRepository(new HttpSessionSecurityContextRepository())
//                )
//                .httpBasic((basic) -> basic
//                        .addObjectPostProcessor(new ObjectPostProcessor<BasicAuthenticationFilter>() {
//                            @Override
//                            public BasicAuthenticationFilter postProcess(BasicAuthenticationFilter filter) {
//                                filter.setSecurityContextRepository(new HttpSessionSecurityContextRepository());
//                                return filter;
//                            }
//                        })
//                )
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
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("password").roles("USER").authorities(SrvcAuthority.MNGM.name())
                .build();
        return new InMemoryUserDetailsManager(user);
    }

//    @Bean
//    public AuthenticationManager authenticationManager() {
//        return new ProviderManager();
//    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthenticationProvider(){

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                return null;
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return false;
            }
        };
    }
}
