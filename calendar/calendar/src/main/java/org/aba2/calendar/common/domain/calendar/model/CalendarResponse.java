package org.aba2.calendar.common.domain.calendar.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.calendar.model.enums.Colors;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarResponse {

    private Long calId;

    private String groupId;

    private String groupName;

    private String userId;

    private String title;

    private String content;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalDate endDate;

    private LocalTime endTime;

    private LocalDateTime ringAt;

    private String place;

    private Colors color;

    private String repeatDay;

}
