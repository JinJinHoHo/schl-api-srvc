package com.vstr.apisrvc.adapter.in.restful.sign;

import com.vstr.apisrvc.application.signature.SessionContext;
import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.exception.AuthException;
import com.vstr.apisrvc.core.http.response.ItemResponse;
import com.vstr.apisrvc.core.http.response.Response;
import com.vstr.apisrvc.core.session.SrvcAuthority;
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

        //로그인 유형 확인.
        SrvcAuthority srvcAuthority = signLoginRequest.getSrvc();
        if (srvcAuthority == null) throw new IllegalArgumentException("로그이 지원 안됨.");

        //기존 로그인 사용자 정보 체크
        //로그인 요청 ID가 기존과 동일 하면 로그인 처리 안함.
        Authentication currentAuthentication = SessionContext.getAuthentication();
        if (currentAuthentication.isAuthenticated()
            && currentAuthentication.getName().equals(signLoginRequest.getUsrId())) {
            return new ItemResponse<>(HttpCode.success, currentAuthentication.getDetails());
        }

        //인증용 토큰 생성.
        Authentication authenticationToken = srvcAuthority.publishAuthenticationToken(
                signLoginRequest.getUsrId(), signLoginRequest.getPswd()
        );

        //인증
        Authentication newAuthentication = authenticationManager.authenticate(authenticationToken);

        //SecurityContext 인증 정보 설정.
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(newAuthentication);

        //securityContext 인증정보 반영.(레디스 반영.)
        securityContextRepository.saveContext(context, request, response);

        return new ItemResponse<>(HttpCode.success, newAuthentication.getDetails());
    }

    @GetMapping("logout")
    public Response logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (!authentication.isAuthenticated()) return Response.getResponseSuccess();

        if (authentication instanceof AbstractAuthenticationToken) {
            ((AbstractAuthenticationToken) authentication).eraseCredentials();
        }

        authentication.setAuthenticated(false);

        securityContextRepository.saveContext(context, request, response);

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
