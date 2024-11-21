package org.aba2.calendar.common.domain.calendar.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.domain.calendar.model.enums.Colors;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CalendarResponse {

    private Long calId;

    private String userId;

    private String title;

    private String content;

    private LocalDateTime startDate;

    private LocalDateTime startTime;

    private LocalDateTime endDate;

    private LocalDateTime endTime;

    private LocalDateTime ringAt;

    private String place;

    private Colors color;

    private String blockYn;

    private String repeatYn;

}
