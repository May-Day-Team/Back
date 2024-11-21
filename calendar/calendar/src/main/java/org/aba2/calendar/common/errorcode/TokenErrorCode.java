package org.aba2.calendar.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * Token 3000~3999
 */

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCodeIfs{


    INVALID_TOKEN(400, 3000, "유효하지 않은 토큰"),

    EXPIRED_TOKEN(400, 3001, "만료된 토큰"),

    TOKEN_EXCEPTION(400, 3002, "알 수 없는 토큰 에러"),

    AUTHORIZATION_TOKEN_NOT_FOUND(400, 3003, "인증 헤더 없음")
    ;

    private final Integer httpStatusCode;

    private final Integer errorCode;

    private final String description;
}