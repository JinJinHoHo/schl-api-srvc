package com.vstr.apisrvc.adapter.in.restful.mngm;

import com.vstr.apisrvc.application.mngm.SchulVisitrService;
import com.vstr.apisrvc.application.signature.SchulMngrUserSession;
import com.vstr.apisrvc.application.signature.SessionContext;
import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.http.response.ItemResponse;
import com.vstr.apisrvc.core.http.response.ListResponse;
import com.vstr.apisrvc.core.http.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/mngm/schul/visitr"})
public class SchulVisitrController {

    private final SchulVisitrService schulVisitrService;

    @GetMapping("page")
    public Response page() {
        return new ItemResponse<>(HttpCode.success,schulVisitrService.getPageContext());
    }

    @GetMapping("list")
    public Response list() {

        SchulMngrUserSession session = SessionContext.getSession(SchulMngrUserSession.class);

        return new ListResponse<>(HttpCode.success, schulVisitrService.list(session.schulNo()));
    }

}
