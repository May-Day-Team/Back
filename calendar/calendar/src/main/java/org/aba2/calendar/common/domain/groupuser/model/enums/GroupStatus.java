package org.aba2.calendar.common.domain.groupuser.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupStatus {

    REJECTION("거절"),
    WAIT("대기"),
    ACCEPTANCE("승락"),
    LEAVE("나가기"),

    ;

    private final String description;
}
