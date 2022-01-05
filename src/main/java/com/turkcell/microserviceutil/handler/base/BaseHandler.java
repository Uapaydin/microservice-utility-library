package com.turkcell.microserviceutil.handler.base;

import com.turkcell.microserviceutil.builder.ResponseBuilder;
import com.turkcell.microserviceutil.enumaration.ReturnType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public class BaseHandler {
    protected  static final Logger LOGGER = LoggerFactory.getLogger(BaseHandler.class);

    protected final ResponseEntity<Map<String, Object>> buildResponseEntity(Exception e, HttpStatus status){
        return new ResponseBuilder(status, ReturnType.FAILURE).withError(e.getMessage()).build();
    }
}
