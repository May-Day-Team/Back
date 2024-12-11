package org.aba2.calendar.common.domain.user.converter;

import org.aba2.calendar.common.errorcode.ErrorCode;
import org.aba2.calendar.common.exception.ApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeConverter {

    public static LocalDateTime convertToLocalDateTime(String input) {
        DateTimeFormatter formatter;
        LocalDate convertedDate;

        // TODO 예외 처리는 하지 않음 문자열 혹은 미래 날짜일 때

        if (input.length() == 6) { // 010925 경우
            int currentYear = LocalDate.now().getYear();
            formatter = DateTimeFormatter.ofPattern("yyMMdd");
            convertedDate = LocalDate.parse(input, formatter);
        } else if (input.length() == 8) { // 20010925 경우
            // yyyyMMdd 포맷으로 처리
            formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            convertedDate = LocalDate.parse(input, formatter);
        } else {
            throw new ApiException(ErrorCode.BAD_REQUEST, "지정되지 않은 날짜입니다.");
        }

        return convertedDate.atStartOfDay();
    }

}
