package org.aba2.calendar.common.domain.accountBook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.aba2.calendar.common.domain.accountBook.model.enums.IncomeExpense;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class AccountBookResponse {
    private Long accountBookId;

    private String description;

    private IncomeExpense incomeExpense;

    private Integer amount;

    private LocalDate date;
}
