package org.aba2.calendar.common.domain.accountBook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IncomeExpense {

    INCOME("Income"),  // 수입
    EXPENSE("Expense"); // 지출

    private final String description;
}
