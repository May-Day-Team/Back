package org.aba2.calendar.common.exception;

import lombok.Getter;
import org.aba2.calendar.common.errorcode.ErrorCodeIfs;


@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs{

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescription;

    public ApiException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescription());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = errorCodeIfs.getDescription();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String description) {
        super(description);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = description;
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Exception e) {
        super(e);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescription = e.getMessage();
    }
}
