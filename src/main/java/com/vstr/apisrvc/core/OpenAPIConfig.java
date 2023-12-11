package com.vstr.apisrvc.core;

import com.vstr.apisrvc.core.security.SrvcAuthority;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.SpecVersion;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementServerProperties;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    private final String AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";

    @Bean
    public OpenAPI openAPI(
            ServerProperties serverProperties,
            ManagementServerProperties managementServerProperties
    ) {

        Integer port = serverProperties.getPort();
        if (port == null) {
            port = 8080;
        }
        return new OpenAPI(SpecVersion.V31)
                .servers(List.of(
                        new Server().url("http://localhost:%d".formatted(port)),
                        new Server().url("http://local.isung.com:%d".formatted(port))
                ))
                .info(new Info()
                        .title("학교 방문자 관리 서비스 API")
                        .version("v0.0.1")
                )
                .security(List.of(new SecurityRequirement()
                        .addList(AUTH_TOKEN_HEADER)
                ))
                .components(
                        new Components()
                                .addSecuritySchemes(
                                        AUTH_TOKEN_HEADER,
                                        new SecurityScheme()
                                                .name("Authorization")
                                                .type(SecurityScheme.Type.HTTP)
                                                .in(SecurityScheme.In.HEADER)
                                                .bearerFormat("JWT")
                                                .scheme("bearer")
                                )
                )
                ;
    }


    @Bean
    public GroupedOpenApi mngmGroup() {
        return getGroupedOpenApi(SrvcAuthority.MNGM);
    }

    @Bean
    public GroupedOpenApi vstrsGroup() {
        return getGroupedOpenApi(SrvcAuthority.VSTRS);
    }

    @Bean
    public GroupedOpenApi optrGroup() {
        return getGroupedOpenApi(SrvcAuthority.OPTR);
    }

    @Bean
    public GroupedOpenApi kioskGroup() {
        return getGroupedOpenApi(SrvcAuthority.KIOSK);
    }


    @Bean
    public GroupedOpenApi signGroup() {
        return getGroupedOpenApi(SrvcAuthority.SIGN);
    }

    @Bean
    public GroupedOpenApi testGroup() {
        return getGroupedOpenApi(SrvcAuthority.TEST);
    }

    private static GroupedOpenApi getGroupedOpenApi(SrvcAuthority srvcAuthority) {
        return GroupedOpenApi.builder()
                .group(srvcAuthority.getName())
                .pathsToMatch(srvcAuthority.getPatterns())
                .build();
    }

}
