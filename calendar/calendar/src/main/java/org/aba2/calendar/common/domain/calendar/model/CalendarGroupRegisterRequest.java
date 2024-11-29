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
@ToString
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CalendarGroupRegisterRequest {

    // 그룹 캘린더의 groupId (그룹 캘린더에서만 사용)
    private String groupId;

    //그룹 이름
    private String groupName;

    // 시작 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    // 종료 날짜
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // 일정 제목
    private String title;

    // 일정 내용
    private String content;

    // 일정 장소
    private String place;

    //반복여부
    private String repeatDay;

    // 일정 시작 시간
    private LocalTime startTime;

    // 일정 종료 시간
    private LocalTime endTime;

    // 일정 알람 시간
    private LocalDateTime ringAt;

    // 일정 색상
    private Colors color;

    // 그룹 캘린더의 멤버들 목록 (이벤트가 그룹 캘린더에 추가될 때, 모든 그룹 멤버의 캘린더에 추가)
    private List<String> groupMembers;
}
