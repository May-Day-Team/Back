package org.aba2.calendar.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RecordErrorCode implements ErrorCodeIfs {

    // 404 Not Found
    RECORD_NOT_FOUND(404, 7000, "해당 일기를 찾을 수 없습니다"),

    // 403 Forbidden
    UNAUTHORIZED_ACCESS(403, 7001, "접근 권한이 없습니다"),

    // 400 Bad Request
    INVALID_PAGE_REQUEST(400, 7002, "잘못된 페이지 요청입니다"), // -1페이지 같은 요청
    VALIDATION_ERROR(400, 7003, "입력값 검증 오류가 발생했습니다."), // VALIDATION_ERROR 추가
    ENUM_NOT_FOUND(400, 7004, "유효하지 않은 ENUM 값입니다");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
