package org.aba2.calendar.common.exception;

import org.aba2.calendar.common.errorcode.ErrorCodeIfs;

public interface ApiExceptionIfs {

    ErrorCodeIfs getErrorCodeIfs();

    String getErrorDescription();

}
