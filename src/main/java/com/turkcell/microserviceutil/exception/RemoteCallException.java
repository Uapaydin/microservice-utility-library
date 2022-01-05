package com.turkcell.microserviceutil.exception;

import com.turkcell.microserviceutil.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class RemoteCallException extends BaseException
{
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;

    public RemoteCallException(String message) {
        super(HTTP_STATUS, message);
    }
}
