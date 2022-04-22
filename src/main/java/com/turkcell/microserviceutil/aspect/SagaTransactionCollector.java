package com.turkcell.microserviceutil.aspect;

import com.turkcell.microserviceutil.model.request.RemoteCallRequest;
import com.turkcell.microserviceutil.service.SagaTransactionHandler;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 13:26
 */
@Aspect
@Configuration
@Slf4j
public class SagaTransactionCollector {

    private final SagaTransactionHandler sagaTransactionHandler;

    public SagaTransactionCollector(SagaTransactionHandler sagaTransactionHandler) {
        this.sagaTransactionHandler = sagaTransactionHandler;
    }

    @Before(value = "@within(Saga) && args(remoteCallRequest,..)")
    public void beforeAdvice(JoinPoint joinPoint, RemoteCallRequest remoteCallRequest) {
        log.warn("Before method:" + joinPoint.getSignature());
        log.warn("called remoteUrl " + remoteCallRequest);
    }

    @After(value = "@within(Saga) && args(remoteCallRequest,..)")
    public void afterAdvice(JoinPoint joinPoint, RemoteCallRequest remoteCallRequest) {
        sagaTransactionHandler.getTransactionHistory().push(remoteCallRequest);
        log.warn("After method:" + joinPoint.getSignature());
        log.warn("call completed remoteUrl " + remoteCallRequest);
    }

}