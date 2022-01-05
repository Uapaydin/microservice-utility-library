package com.turkcell.microserviceutil.exception;

import com.turkcell.microserviceutil.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class RequestException extends BaseException
{
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "Request format is not correct.";
    public RequestException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
