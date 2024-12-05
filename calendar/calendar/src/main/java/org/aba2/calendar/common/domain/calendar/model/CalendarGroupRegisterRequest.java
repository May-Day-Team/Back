package org.aba2.calendar.common.domain.calendar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.aba2.calendar.common.domain.calendar.model.enums.Colors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CalendarGroupRegisterRequest extends BaseCalendarRequest{

    // 그룹 캘린더의 groupId (그룹 캘린더에서만 사용)
    private String groupId;

    //그룹 이름
    private String groupName;

}
