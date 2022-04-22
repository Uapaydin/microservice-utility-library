package com.turkcell.microserviceutil.handler;

import com.turkcell.microserviceutil.builder.ResponseBuilder;
import com.turkcell.microserviceutil.config.kafka.KafkaProducerConfig;
import com.turkcell.microserviceutil.enumaration.ReturnType;
import com.turkcell.microserviceutil.exception.SagaTransactionException;
import com.turkcell.microserviceutil.handler.base.BaseHandler;
import com.turkcell.microserviceutil.model.request.RemoteCallPackage;
import com.turkcell.microserviceutil.model.request.RemoteCallRequest;
import com.turkcell.microserviceutil.model.request.RemoteKafkaCallPackage;
import com.turkcell.microserviceutil.model.request.RemoteRestCallPackage;
import com.turkcell.microserviceutil.service.RemoteCall;
import com.turkcell.microserviceutil.service.SagaTransactionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 15:57
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SagaExceptionHandler extends BaseHandler {

    private final SagaTransactionHandler sagaTransactionHandler;
    private final RemoteCall remoteCall;
    private final KafkaTemplate kafkaTemplate;

    public SagaExceptionHandler(SagaTransactionHandler sagaTransactionHandler, KafkaTemplate kafkaTemplate, RemoteCall remoteCall) {
        this.sagaTransactionHandler = sagaTransactionHandler;
        this.kafkaTemplate = kafkaTemplate;
        this.remoteCall = remoteCall;
    }

    @ExceptionHandler(SagaTransactionException.class)
    public final ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        while (!sagaTransactionHandler.getTransactionHistory().isEmpty()){
            RemoteCallRequest transaction =  sagaTransactionHandler.getTransactionHistory().pop();
            if(transaction.getCompensationRequest() instanceof RemoteKafkaCallPackage){
                RemoteKafkaCallPackage compensationPackage = (RemoteKafkaCallPackage) transaction.getCompensationRequest();
                kafkaTemplate.send(compensationPackage.getServiceName(),compensationPackage.getKafkaMessage());
            }else {
                remoteCall.callForCompensation(transaction, Boolean.class);
            }

        }

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return new ResponseBuilder(HttpStatus.INTERNAL_SERVER_ERROR, ReturnType.FAILURE).withError(errors).build();
    }

}
