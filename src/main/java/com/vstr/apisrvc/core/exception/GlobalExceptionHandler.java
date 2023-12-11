package com.vstr.apisrvc.core.exception;


import com.vstr.apisrvc.core.code.HttpCode;
import com.vstr.apisrvc.core.response.ErrorResponse;
import com.vstr.apisrvc.core.response.ExceptionErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleNoSuchElementFoundException(Exception e) {

        ErrorResponse errorResponse;

        HttpStatusCode status = HttpStatus.OK;

        switch (e) {
            case BizException ex ->
                //비지니스 처리시 발생된 에러 반환.
                    errorResponse = new ErrorResponse(ex.getCode());
            case AuthException ex -> {
                //비지니스 로직을 제외한 서버오류에 대한 반환 처리(설정 및 DB 연동 장애 등등.)
                log.error("Auth Error : {}", ex.toString());
                log.catching(e);
                errorResponse = new ErrorResponse(ex.getCode());
            }
            case WebExchangeBindException ex -> {
                //@Vaild 로 검증하다 발생된 에러가 있을 경우에 대한 처리.
                Map<String, String> collect = ex.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .collect(Collectors
                                .toMap(
                                        fieldError -> ((FieldError) fieldError).getField(),
                                        fieldError -> fieldError.getDefaultMessage()
                                )
                        );
                errorResponse = new VaildErrorResponse(HttpCode.vaild_error, ex.getReason(), collect);
            }
            case ResponseStatusException ex -> {
                //런타임  오류에 발생에 대한 처리
                log.error("Server Error : {}", ex.toString());
                log.catching(e);
                status = HttpStatus.resolve(ex.getBody().getStatus());
                if (status == null) status = HttpStatus.INTERNAL_SERVER_ERROR;
                errorResponse = new ExceptionErrorResponse(HttpCode.error, ex.getClass().getSimpleName(), ex.getMessage());
            }
            case org.springframework.web.ErrorResponse ex -> {
                //런타임  오류에 발생에 대한 처리
                status = ex.getStatusCode();
                errorResponse = new ErrorResponse(HttpCode.error_response, status.toString());
            }
            case null, default -> {
                //런타임  오류에 발생에 대한 처리
                log.error("Server Error : {}", e.toString());
                log.catching(e);
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                errorResponse = new ExceptionErrorResponse(HttpCode.error, e.getClass().getSimpleName(), e.getMessage());
            }
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}
