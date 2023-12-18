package com.vstr.apisrvc.adapter.in.restful.sign;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.exception.AuthException;
import com.vstr.apisrvc.core.http.response.ItemResponse;
import com.vstr.apisrvc.core.http.response.Response;
import com.vstr.apisrvc.core.security.MngmUserAuthenticationToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/sign"})
@RequiredArgsConstructor
public class SignController {

    private final SecurityContextRepository securityContextRepository;

    private final AuthenticationManager authenticationManager;

    @PostMapping("login")
    public Response login(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody SignLoginRequest signLoginRequest) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication beforeAuthentication = context.getAuthentication();

        //기존 로그인 사용자 정보 체크
        //로그인 요청 ID가 기존과 동일 하면 로그인 처리 안함.
        if (beforeAuthentication.isAuthenticated()
            && beforeAuthentication.getName().equals(signLoginRequest.getUsrId())) {
            return new ItemResponse<>(HttpCode.success, beforeAuthentication.getDetails());

        }

        //인증용 토큰 생성.
        Authentication token = MngmUserAuthenticationToken.unauthenticated(
                signLoginRequest.getUsrId(), signLoginRequest.getPswd());

        //인증
        Authentication authentication = authenticationManager.authenticate(token);

        //SecurityContext 인증 정보 설정.
        context.setAuthentication(authentication);

        //securityContext 인증정보 반영.(레디스 반영.)
        securityContextRepository.saveContext(context, request, response);

        return new ItemResponse<>(HttpCode.success, authentication.getDetails());
    }

    @GetMapping("logout")
    public Response logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication.isAuthenticated()) {
            if (authentication instanceof AbstractAuthenticationToken) {
                ((AbstractAuthenticationToken) authentication).eraseCredentials();
            }
            authentication.setAuthenticated(false);
            securityContextRepository.saveContext(context, request, response);
        }
        return Response.getResponseSuccess();
    }

    @GetMapping("user")
    public Response user() {

        SecurityContext context = SecurityContextHolder.getContext();

        Authentication authentication = context.getAuthentication();
        if (!authentication.isAuthenticated()) {
            throw new AuthException("권한 없음.");
        }

        return new ItemResponse<>(authentication.getDetails());
    }
}
