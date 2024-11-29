package org.aba2.calendar.common.domain.calendar.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class CalendarRegisterRequest {

    // 시작 날짜
    @NotNull(message = "시작 날짜는 필수 입력값입니다.")
    private LocalDate startDate;

    // 종료 날짜
    @NotNull(message = "종료 날짜는 필수 입력값입니다.")
    private LocalDate endDate;

    // 일정 제목
    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    // 일정 내용
    private String content;

    // 일정 장소
    private String place;

    //반복여부
    private String repeatDay;

    // 일정 시작 시간
    @NotNull(message = "시작 시간은 필수 입력값입니다.")
    private LocalTime startTime;

    // 일정 종료 시간
    @NotNull(message = "종료 시간은 필수 입력값입니다.")
    private LocalTime endTime;

    // 일정 알람 시간
    private LocalDateTime ringAt;

    // 일정 색상
    private Colors color;

}
