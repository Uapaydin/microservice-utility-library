package com.turkcell.microserviceutil.exception;

import com.turkcell.microserviceutil.exception.base.BaseException;
import org.springframework.http.HttpStatus;

public class AuthorizationException extends BaseException
{
    private static final HttpStatus HTTP_STATUS = HttpStatus.UNAUTHORIZED;
    private static final String MESSAGE = "Unauthorized access.";

    public AuthorizationException() {
        super(HTTP_STATUS, MESSAGE);
    }
}
