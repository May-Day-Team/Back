package org.aba2.calendar.common.domain.accountBook.dto;

import lombok.*;
import org.aba2.calendar.common.domain.accountBook.model.enums.IncomeExpense;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class AccountBookDetailResponse {
    private Long id;

    private String description;

    private IncomeExpense incomeExpense;

    private Integer amount;

    private LocalDate date;
}
