package org.aba2.calendar.common.domain.record.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.aba2.calendar.common.domain.accountBook.model.enums.IncomeExpense;
import org.aba2.calendar.common.errorcode.RecordErrorCode;
import org.aba2.calendar.common.exception.ApiException;

@Getter
@AllArgsConstructor
public enum Weather {

    SUNNY("Sunny"),
    RAINY("Rainy"),
    CLOUDY("Cloudy"),
    SNOWY("Snowy");

    private final String weather;

    public static Weather get(String weather) {
        for (Weather w : Weather.values()) {
            if (w.weather.equals(weather)) {
                return w;
            }
        }
        throw new ApiException(RecordErrorCode.ENUM_NOT_FOUND, "유효하지 않은 ENUM 값입니다.");
    }
}
