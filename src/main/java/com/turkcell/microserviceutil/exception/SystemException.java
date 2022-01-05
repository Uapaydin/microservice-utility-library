package com.turkcell.microserviceutil.exception;

import com.turkcell.microserviceutil.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class SystemException extends BaseException

{
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String MESSAGE = "System error occured.";

    public SystemException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
