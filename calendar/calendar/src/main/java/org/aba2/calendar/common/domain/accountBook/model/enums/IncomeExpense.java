package org.aba2.calendar.common.domain.accountBook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IncomeExpense {

    INCOME("INCOME","+"),  // 수입
    EXPENSE("EXPENSE", "-"); // 지출

    private final String incomeExpense;
    private final String symbol;

    // +나 -일때만 변환함
    public static IncomeExpense get(String symbol) {
        for (IncomeExpense value : IncomeExpense.values()) {
            if (value.symbol.equals(symbol)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid symbol: " + symbol);
    }
}
