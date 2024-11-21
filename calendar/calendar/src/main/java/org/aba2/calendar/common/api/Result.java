package org.aba2.calendar.common.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aba2.calendar.common.errorcode.ErrorCodeIfs;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result {

    // Http 통신 코드
    private Integer resultCode;

    // 간단한 메세지
    private String resultMessage;

    // 상세 메세지
    private String resultDescription;


    public static Result OK() {
        return Result.builder()
                .resultCode(200)
                .resultMessage("OK")
                .resultDescription("성공")
                .build()
                ;
    }


    public static Result ERROR(ErrorCodeIfs errorCodeIfs) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage("Error")
                .resultDescription(errorCodeIfs.getDescription())
                .build()
                ;
    }

    public static Result ERROR(ErrorCodeIfs errorCodeIfs, String description) {
        return Result.builder()
                .resultCode(errorCodeIfs.getErrorCode())
                .resultMessage("Error")
                .resultDescription(description)
                .build()
                ;
    }

}
