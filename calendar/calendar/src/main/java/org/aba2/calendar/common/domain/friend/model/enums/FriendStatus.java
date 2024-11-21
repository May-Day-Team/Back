package org.aba2.calendar.common.domain.friend.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendStatus {

    REJECTION("거절"),
    WAIT("대기"),
    ACCEPTANCE("승락"),
    BLOCK("차단"),
    DELETE("삭제")
    ;

    private final String description;
}
