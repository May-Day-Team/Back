package org.aba2.calendar.common.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendErrorCode implements ErrorCodeIfs{

    FRIEND_NOT_FOUND(400, 5404, "친구 목록에 없습니다."),

    ;

    private final Integer httpStatusCode;

    private final Integer errorCode;

    private final String description;
}
