package org.aba2.calendar.common.domain.record.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class RecordResponse {
    private String title;

    private String content;

    private String weather;

    private LocalDate updateAt;
}
