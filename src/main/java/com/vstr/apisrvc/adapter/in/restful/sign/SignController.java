package com.vstr.apisrvc.adapter.in.restful.sign;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.response.ItemResponse;
import com.vstr.apisrvc.core.response.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.*;

@RestController

@RequestMapping(value = {"/sign"})
public class SignController {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public SignController(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }


    @PostMapping("login")
    public Response login(
            HttpServletRequest request,
            HttpServletResponse response,
            @Valid @RequestBody SignLoginRequest signLoginRequest) {


        UsernamePasswordAuthenticationToken token = UsernamePasswordAuthenticationToken.unauthenticated(
                signLoginRequest.getUsrId(), signLoginRequest.getPswd());

        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(authentication);
        securityContextRepository.saveContext(context, request, response);

        return new ItemResponse<>(HttpCode.success, null);
    }

    @GetMapping("logout")
    public Response logout() {
        return new Response(HttpCode.success);
    }
}
