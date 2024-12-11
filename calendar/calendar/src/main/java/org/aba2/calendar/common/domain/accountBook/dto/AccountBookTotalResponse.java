package org.aba2.calendar.common.domain.accountBook.dto;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter @Setter
public class AccountBookTotalResponse {
    private LocalDate date;

    private Integer total;
}
