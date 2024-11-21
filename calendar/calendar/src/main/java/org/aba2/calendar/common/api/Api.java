package org.aba2.calendar.common.api;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.errorcode.ErrorCodeIfs;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Api<T> {

    private Result result;

    @Valid
    private T body;


    // 성공
    public static <T>Api<T> OK(T data) {
        return Api.<T>builder()
                .result(Result.OK())
                .body(data)
                .build();
    }

    // 이미 저장된 메세지 출력
    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs) {
        return Api.builder()
                .result(Result.ERROR(errorCodeIfs))
                .body(errorCodeIfs.getDescription())
                .build()
                ;
    }

    // 원하는 메세지 출력
    public static Api<Object> ERROR(ErrorCodeIfs errorCodeIfs, String description) {
        return Api.builder()
                .result(Result.ERROR(errorCodeIfs, description))
                .body(description)
                .build()
                ;
    }

}
