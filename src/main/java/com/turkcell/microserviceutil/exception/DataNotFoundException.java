package com.turkcell.microserviceutil.exception;

import com.turkcell.microserviceutil.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class DataNotFoundException extends BaseException
{
    private static final String MESSAGE = "Data you requested was not found.";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;

    public DataNotFoundException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
