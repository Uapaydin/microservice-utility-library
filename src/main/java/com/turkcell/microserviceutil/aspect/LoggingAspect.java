package com.turkcell.microserviceutil.aspect;

import com.turkcell.microserviceutil.annotation.Loggable;
import com.turkcell.microserviceutil.enumaration.LoggingDirection;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {

    @Before(value = "@within(loggable)")
    public void logInputParameterOfMethods(JoinPoint jointPoint, Loggable loggable) {
        String classNameAndMethodName = getClassNameAndMethodName(jointPoint);
        Object[] signatureArgs = jointPoint.getArgs();
        if (isLogLevelForLoggableInfo(loggable)) {
            log.info("Input parameters of {} are: {}", classNameAndMethodName, signatureArgs);
        } else if (log.isDebugEnabled()) {
            log.debug("Input parameters of {} are: {}", classNameAndMethodName, signatureArgs);
        }
    }

    @AfterReturning(value = "@within(loggable)", returning = "result")
    public void logReturnParametersOfMethods(JoinPoint joinPoint, Loggable loggable, Object result) {
        String classNameAndMethodName = getClassNameAndMethodName(joinPoint);
        if (isLogLevelForLoggableInfo(loggable)) {
            if (result != null) {
                ResponseEntity<Object> responseEntity = (ResponseEntity<Object>) result;
                if (responseEntity.getStatusCode() != HttpStatus.OK) {
                    log.info("Return parameters of {} are: {}", classNameAndMethodName, result);
                } else {
                    log.info("Request returned from {} with status code: {}", classNameAndMethodName, responseEntity.getStatusCode());
                }
            } else {
                log.info("Return parameters of {} is null value. ", classNameAndMethodName);
            }
        }else {
            if (result != null) {
                log.info("Return parameters of {} are: {}", classNameAndMethodName, result);
            } else {
                log.info("Request returned from {} is null value.", classNameAndMethodName);
            }
        }
    }

    private boolean isLogLevelForLoggableInfo(Loggable loggable) {
        return loggable.type().getLogLevel() == LogLevel.INFO;
    }

    private String getClassNameAndMethodName(JoinPoint joinpoint){
        MethodSignature methodSignature = (MethodSignature) joinpoint.getSignature();
        return methodSignature.getDeclaringType().getSimpleName() + "." + methodSignature.getName();
    }
}
