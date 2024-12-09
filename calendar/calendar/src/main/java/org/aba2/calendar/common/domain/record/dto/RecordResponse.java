package org.aba2.calendar.common.domain.record.dto;

import lombok.*;
import org.aba2.calendar.common.domain.record.model.enums.Weather;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class RecordResponse {
    private String title;

    private String content;

    private Weather weather;

    private LocalDate updateAt;
}
