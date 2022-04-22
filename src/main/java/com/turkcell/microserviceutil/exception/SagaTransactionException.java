package com.turkcell.microserviceutil.exception;

import com.turkcell.microserviceutil.exception.base.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 15:59
 */
public class SagaTransactionException extends BaseException {
    private static final String MESSAGE = "Transaction throw for compensation";
    public SagaTransactionException(HttpStatus httpStatus, String message) {
        super(httpStatus, StringUtils.hasText(message)?message:MESSAGE);
    }

    public SagaTransactionException(HttpStatus httpStatus) {
        super(httpStatus);
    }
}
