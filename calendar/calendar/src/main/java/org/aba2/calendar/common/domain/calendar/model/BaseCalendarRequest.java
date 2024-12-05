package org.aba2.calendar.common.domain.calendar.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.calendar.model.enums.Colors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseCalendarRequest {

    @NotNull(message = "시작 날짜는 필수 입력값입니다.")
    private LocalDate startDate;

    @NotNull(message = "종료 날짜는 필수 입력값입니다.")
    private LocalDate endDate;

    @NotBlank(message = "제목은 필수 입력값입니다.")
    private String title;

    //일정내용
    private String content;

    //장소
    private String place;

    //반복여부
    private String repeatDay;

    @NotNull(message = "시작 시간은 필수 입력값입니다.")
    private LocalTime startTime;

    @NotNull(message = "종료 시간은 필수 입력값입니다.")
    private LocalTime endTime;

    //일정 알람시간
    private LocalDateTime ringAt;

    //일정색상
    private Colors color;

}
