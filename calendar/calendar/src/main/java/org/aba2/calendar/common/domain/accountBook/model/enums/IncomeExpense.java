package org.aba2.calendar.common.domain.accountBook.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aba2.calendar.common.errorcode.RecordErrorCode;
import org.aba2.calendar.common.exception.ApiException;

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
        throw new ApiException(RecordErrorCode.ENUM_NOT_FOUND, "유효하지 않은 ENUM 값입니다.");
    }
}
