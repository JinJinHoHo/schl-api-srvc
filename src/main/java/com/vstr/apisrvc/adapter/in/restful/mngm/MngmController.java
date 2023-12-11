package com.vstr.apisrvc.adapter.in.restful.mngm;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/mngm"})
public class MngmController {

    @GetMapping("mngm")
    public Response test(){
        return new Response(HttpCode.success);
    }


}
