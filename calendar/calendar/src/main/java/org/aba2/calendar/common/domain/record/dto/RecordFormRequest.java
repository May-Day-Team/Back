package org.aba2.calendar.common.domain.record.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordFormRequest {

    @NotBlank(message = "제목은 필수 항목입니다.")
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    @Size(max = 1000)
    private String content;

    @Pattern(regexp = "^(Sunny|Rainy|Cloudy|Snowy)$", message = "올바른 날씨 값을 입력해주세요.")
    private String weather;

    @NotBlank(message = "작성/수정할 날짜를 정해주세요")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate createAt;
}
