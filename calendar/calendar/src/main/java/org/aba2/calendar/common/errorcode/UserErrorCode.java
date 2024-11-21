package org.aba2.calendar.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * User Error 1000~1999
 */

@Getter
@AllArgsConstructor
public enum UserErrorCode implements ErrorCodeIfs{

    USER_NOT_FOUND(400, 1404, "유저를 찾을 수 없습니다"),

    LOGIN_FAILED(401, 1403, "아이디 혹은 비밀번호가 틀립니다"),

    DUPLICATE_USER_ID(401, 1402, "이미 존재하는 아이디입니다."),


    ;


    private final Integer httpStatusCode;

    private final Integer errorCode;

    private final String description;
}
