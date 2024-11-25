package org.aba2.calendar.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupErrorCode implements ErrorCodeIfs{

    GROUP_NOT_FOUND(400, 6404, "그룹을 찾을 수 없음"),

    ;

    private final Integer httpStatusCode;

    private final Integer errorCode;

    private final String description;
}
