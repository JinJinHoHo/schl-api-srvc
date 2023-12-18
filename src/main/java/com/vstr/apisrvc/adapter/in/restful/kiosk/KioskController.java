package com.vstr.apisrvc.adapter.in.restful.kiosk;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.http.response.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kiosk"})
public class KioskController {

    @GetMapping("kiosk")
    public Response test(){
        return new Response(HttpCode.success);
    }


}
