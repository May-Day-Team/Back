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
    INVALID_PAGE_REQUEST(400, 7002, "잘못된 페이지 요청입니다"); // -1페이지 같은 요청

    private final Integer httpStatusCode;
    private final Integer errorCode;
    private final String description;
}
