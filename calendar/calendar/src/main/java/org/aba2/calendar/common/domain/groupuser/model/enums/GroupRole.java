package org.aba2.calendar.common.domain.groupuser.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupRole {

    ADMIN("방장"),
    USER("사용자")

    ;

    private final String descriptioin;
}
