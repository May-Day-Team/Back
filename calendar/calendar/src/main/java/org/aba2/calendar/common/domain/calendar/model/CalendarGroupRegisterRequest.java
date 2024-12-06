package org.aba2.calendar.common.domain.calendar.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarGroupRegisterRequest extends BaseCalendarRequest{

    // 그룹 캘린더의 groupId (그룹 캘린더에서만 사용)
    private String groupId;

    //그룹 이름
    private String groupName;

}
