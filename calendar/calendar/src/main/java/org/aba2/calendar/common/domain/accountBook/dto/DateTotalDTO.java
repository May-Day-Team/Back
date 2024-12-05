package org.aba2.calendar.common.domain.accountBook.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class DateTotalDTO {
    private LocalDate date; // 날짜
    private int totalAmount; // 해당 날짜의 총합 (손익 포함)
}
