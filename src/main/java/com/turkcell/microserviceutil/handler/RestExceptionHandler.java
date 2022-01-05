package com.turkcell.microserviceutil.handler;

import com.turkcell.microserviceutil.builder.ResponseBuilder;
import com.turkcell.microserviceutil.enumaration.ReturnType;
import com.turkcell.microserviceutil.handler.base.BaseHandler;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler extends BaseHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ResponseBuilder(HttpStatus.BAD_REQUEST, ReturnType.FAILURE).withError(errors).build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Map<String, Object>> handleConstraintViolationException(ConstraintViolationException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        if (!CollectionUtils.isEmpty(violations)) {
            violations.forEach(x -> errors.add(x.getMessage()));
        } else {
            errors.add("Undefined ConstraintViolationException");
        }
        return new ResponseBuilder(HttpStatus.BAD_REQUEST, ReturnType.FAILURE).withError(errors).build();
    }


    @ExceptionHandler({HttpRequestMethodNotSupportedException.class, HttpMediaTypeNotSupportedException.class, HttpMediaTypeNotAcceptableException.class, MissingPathVariableException.class,
            MissingServletRequestParameterException.class, ServletRequestBindingException.class, ConversionNotSupportedException.class, TypeMismatchException.class, HttpMessageNotReadableException.class,
            HttpMessageNotWritableException.class, MissingServletRequestPartException.class, BindException.class, NoHandlerFoundException.class, AsyncRequestTimeoutException.class})
    public final ResponseEntity<Map<String, Object>> handleException(Exception ex, WebRequest request) {
        HttpStatus status;
        LOGGER.error("Exception thrown at RestExceptionHandler ", ex);
        if (ex instanceof HttpRequestMethodNotSupportedException) {
            status = HttpStatus.METHOD_NOT_ALLOWED;
            return buildResponseEntity(ex, status);
        } else if (ex instanceof HttpMediaTypeNotSupportedException) {
            status = HttpStatus.UNSUPPORTED_MEDIA_TYPE;
            return buildResponseEntity(ex, status);
        } else if (ex instanceof HttpMediaTypeNotAcceptableException) {
            status = HttpStatus.NOT_ACCEPTABLE;
            return buildResponseEntity(ex, status);
        } else if (ex instanceof MissingPathVariableException || ex instanceof ConversionNotSupportedException
                ||ex instanceof HttpMessageNotWritableException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return buildResponseEntity(ex, status);
        } else if (ex instanceof ServletRequestBindingException || ex instanceof TypeMismatchException
                || ex instanceof HttpMessageNotReadableException || ex instanceof MissingServletRequestPartException
                || ex instanceof BindException) {
            status = HttpStatus.BAD_REQUEST;
            return buildResponseEntity(ex, status);
        } else if (ex instanceof NoHandlerFoundException) {
            status = HttpStatus.NOT_FOUND;
            return buildResponseEntity(ex, status);
        } else if (ex instanceof AsyncRequestTimeoutException) {
            status = HttpStatus.SERVICE_UNAVAILABLE;
            return buildResponseEntity(ex, status);
        } else {
            return buildResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

