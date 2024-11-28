package org.aba2.calendar.common.domain.calendar.model;

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
public class CalendarRegisterRequest {

    // 시작 날짜
    private LocalDate startDate;

    // 종료 날짜
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

}
