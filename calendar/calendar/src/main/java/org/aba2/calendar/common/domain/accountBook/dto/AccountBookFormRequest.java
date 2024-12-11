package org.aba2.calendar.common.domain.accountBook.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountBookFormRequest {

    private Long id;

    @NotBlank(message = "설명은 필수 항목입니다")
    @Size(max = 50)
    private String description;

    @NotBlank(message = "수익인지 지출인지 알 수 없어요")
    private String incomeExpense;

    @NotBlank(message = "금액은 필수 항목입니다")
    @Min(value = 0, message = "금액은 0 이상이여야 합니다")
    private Integer amount;
    
    @NotBlank(message = "수익/지출한 날짜를 정해주세요")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
}
