package org.aba2.calendar.common.domain.diary.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DiaryRequest {

    @NotBlank(message = "제목은 필수 항목입니다.")
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank(message = "내용은 필수 항목입니다.")
    @Size(max = 1000)
    private String content;

    private String tag;

    @Pattern(regexp = "^(Sunny|Rainy|Cloudy|Snowy)$", message = "올바른 날씨 값을 입력해주세요.")
    private String weather;
}
