package org.aba2.calendar.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CalendarErrorCode implements ErrorCodeIfs {

    MISSING_REQUIRED_FIELDS(400, 2001, "필수 입력값이 누락되었습니다."),
    INVALID_DATE_RANGE(400, 2002, "유효하지 않은 날짜 범위입니다."),
    VALIDATION_ERROR(400, 2003, "입력값 검증 오류가 발생했습니다."), // VALIDATION_ERROR 추가
    UNKNOWN_ERROR(500, 2000, "알 수 없는 오류가 발생했습니다.");

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
