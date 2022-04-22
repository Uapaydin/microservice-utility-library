package com.turkcell.microserviceutil.handler;

import com.turkcell.microserviceutil.exception.BusinessException;
import com.turkcell.microserviceutil.exception.DataNotFoundException;
import com.turkcell.microserviceutil.exception.RemoteCallException;
import com.turkcell.microserviceutil.exception.RequestException;
import com.turkcell.microserviceutil.exception.SystemException;
import com.turkcell.microserviceutil.exception.AuthorizationException;
import com.turkcell.microserviceutil.exception.base.BaseException;
import com.turkcell.microserviceutil.handler.base.BaseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE + 10)
public class GlobalRestControllerExceptionHandler extends BaseHandler {
    protected  static final Logger LOGGER = LoggerFactory.getLogger(GlobalRestControllerExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String,Object>> handleBusinessException (BusinessException e, HttpServletRequest request, HttpServletResponse response){
        LOGGER.error("BusinessException caught at GlobalRestControllerExceptionHandler: ", e);
        return handleException(e, request, e.getHttpStatus());
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String,Object>> handleDataNotFoundException (DataNotFoundException e, HttpServletRequest request, HttpServletResponse response){
        LOGGER.error("DataNotFoundException caught at GlobalRestControllerExceptionHandler: ", e);
        return handleException(e, request, e.getHttpStatus());
    }

    @ExceptionHandler(RemoteCallException.class)
    public ResponseEntity<Map<String,Object>> handleRemoteCallException (RemoteCallException e, HttpServletRequest request, HttpServletResponse response){
        LOGGER.error("RemoteCallException caught at GlobalRestControllerExceptionHandler: ", e);
        return handleException(e, request, e.getHttpStatus());
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity<Map<String,Object>> handleRemoteCallException (RequestException e, HttpServletRequest request, HttpServletResponse response){
        LOGGER.error("RequestException caught at GlobalRestControllerExceptionHandler: ", e);
        return handleException(e, request, e.getHttpStatus());
    }

    @ExceptionHandler(SystemException.class)
    public ResponseEntity<Map<String,Object>> handleRemoteCallException (SystemException e, HttpServletRequest request, HttpServletResponse response){
        LOGGER.error("SystemException caught at GlobalRestControllerExceptionHandler: ", e);
        return handleException(e, request, e.getHttpStatus());
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Map<String,Object>> handleRemoteCallException (AuthorizationException e, HttpServletRequest request, HttpServletResponse response){
        LOGGER.error("UnauthorizedAccessException caught at GlobalRestControllerExceptionHandler: ", e);
        return handleException(e, request, e.getHttpStatus());
    }

    private ResponseEntity<Map<String, Object>> handleException(BaseException ex, HttpServletRequest request, HttpStatus status) {

        Map<String, Object> body = new HashMap<>();
        body.put("message", ex.getMessage());
        body.put("status", status.value());
        body.put("uri", request.getRequestURI());

        return new ResponseEntity<>(body, status);

    }

 /*   public ResponseEntity<Map<String,Object>> handleHttpStatusCodeException (HttpStatusCodeException e, HttpServletRequest request, HttpServletResponse response){
        LOGGER.error("handleHttpStatusCodeException caught at GlobalRestControllerExceptionHandler: ",e);
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            Map<String,Object> map = mapper.readValue(e.getLocalizedMessage(),Map.class);
            return new ResponseEntity<>(map,e.getStatusCode());
        } catch (Exception exp) {
            ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(exp.getClass(),ResponseStatus.class);
            return buildResponseEntity(exp,responseStatusAnnotation != null? responseStatusAnnotation.value(): HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> handleNoHandlerFound (Exception e, HttpServletRequest request, HttpServletResponse response){
        ResponseStatus responseStatusAnnotation = AnnotationUtils.findAnnotation(e.getClass(),ResponseStatus.class);
        LOGGER.error("Exception caught at GlobalRestControllerExceptionHandler: ",e);
        return buildResponseEntity(e,responseStatusAnnotation != null? responseStatusAnnotation.value(): HttpStatus.INTERNAL_SERVER_ERROR);
    }
    */
}
