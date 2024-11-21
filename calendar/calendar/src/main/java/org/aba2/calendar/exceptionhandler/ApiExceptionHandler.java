package org.aba2.calendar.exceptionhandler;

import org.aba2.calendar.common.api.Api;
import org.aba2.calendar.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 가장 최우선 Exception Handler
@Order(value = Integer.MIN_VALUE)
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiExceptionHandler(
            ApiException apiException
    ) {
        var errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(Api.ERROR(errorCode, apiException.getErrorDescription()))
                ;
    }

}
