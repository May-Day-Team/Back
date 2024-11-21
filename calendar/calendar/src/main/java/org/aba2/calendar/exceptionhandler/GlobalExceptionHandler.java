//package org.aba2.calendar.exceptionhandler;
//
//import org.aba2.calendar.common.api.Api;
//import org.aba2.calendar.common.errorcode.ErrorCode;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@Order // 우선순위 최하위
//@ControllerAdvice
//public class GlobalExceptionHandler {
//    /**
//     * 서버에서 예상치 못한 에러 발생시 잡아주는 클래스
//     */
//    @ExceptionHandler(value = Exception.class)
//    public ResponseEntity<Object> globalHandler(Exception e) {
//        return ResponseEntity
//                .status(500)
//                .body(Api.ERROR(ErrorCode.SERVER_ERROR, e.toString()))
//                ;
//    }
//}
