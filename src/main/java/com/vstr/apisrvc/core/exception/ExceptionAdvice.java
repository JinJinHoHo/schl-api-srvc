package com.vstr.apisrvc.core.exception;

import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.http.response.ErrorResponse;
import com.vstr.apisrvc.core.http.response.ExceptionErrorResponse;
import com.vstr.apisrvc.core.http.response.Response;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Log4j2
public class ExceptionAdvice {

    @ExceptionHandler(AuthException.class)
    public Response authException(AuthException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(BizException.class)
    public Response bizException(BizException e) {
        return new ErrorResponse(e.getCode(), e.getMessage());
    }


    @ExceptionHandler(AuthenticationException.class)
    public Response authenticationException(AuthenticationException e) {
        return new ErrorResponse(HttpCode.unauthorized, e.getMessage());
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public Response webExchangeBindException(WebExchangeBindException e) {
        Map<String, String> collect = e.getBindingResult()
                .getAllErrors()
                .stream()
                .collect(Collectors
                        .toMap(
                                fieldError -> ((FieldError) fieldError).getField(),
                                fieldError -> {
                                    String message = fieldError.getDefaultMessage();
                                    return message == null ? "" : message;
                                }
                        )
                );
        return new VaildErrorResponse(HttpCode.vaild_error, e.getReason(), collect);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Response> responseStatusException(ResponseStatusException e) {
        log.error("Server Error : {}", e.toString());
        log.catching(e);
        HttpStatus status = HttpStatus.resolve(e.getBody().getStatus());
        if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(new ExceptionErrorResponse(HttpCode.error, e.getClass().getSimpleName(), e.getMessage()), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> exception(Exception e) {
        if (e instanceof org.springframework.web.ErrorResponse) {
            return new ResponseEntity<>(
                    new ErrorResponse(HttpCode.error_response, ((org.springframework.web.ErrorResponse) e).getStatusCode().toString()),
                    ((org.springframework.web.ErrorResponse) e).getStatusCode()
            );
        }
        log.error("Server Error : {}", e.toString());
        log.catching(e);
        return new ResponseEntity<>(
                new ExceptionErrorResponse(HttpCode.error, e.getClass().getSimpleName(), e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
