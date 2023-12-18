package com.vstr.apisrvc.adapter.in.restful.test;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.exception.BizException;
import com.vstr.apisrvc.core.http.response.ItemResponse;
import com.vstr.apisrvc.core.http.response.Response;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/test"})
@Log4j2
public class TestController {

    @GetMapping("test")
    public Response test() {
        return new Response(HttpCode.success);
    }

    @GetMapping("exception")
    public Response exception() {
        throw new BizException(HttpCode.error, new Exception());
    }

    @GetMapping("session")
    public Response session(HttpSession session) {
        log.info(session.getId());
        Map<String, Object> m = new HashMap<>();
        session.getAttributeNames().asIterator().forEachRemaining(s -> m.put(s, session.getAttribute(s)));
        return new ItemResponse<>(HttpCode.success, m);
    }

    @GetMapping("putsession")
    public Response putSession(HttpSession session,
                               @RequestParam String key,
                               @RequestParam String value) {
        log.info(session.getId());

        session.setAttribute(key, value);
        Map<String, Object> m = new HashMap<>();
        session.getAttributeNames()
                .asIterator()
                .forEachRemaining(s -> m.put(s, session.getAttribute(s)));
        return new ItemResponse<>(HttpCode.success, m);
    }
}
