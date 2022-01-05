package com.turkcell.microserviceutil.intercepter;

import com.turkcell.microserviceutil.enumaration.ServletAttribute;
import com.turkcell.microserviceutil.model.User;
import com.turkcell.microserviceutil.service.AuthenticationService;
import com.turkcell.microserviceutil.util.MDCUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@Slf4j
public class GeneralInterceptor  implements HandlerInterceptor
{

    private final AuthenticationService authenticationService;

    public GeneralInterceptor(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(ServletAttribute.START_TIME.getKey(), System.currentTimeMillis());
        String requestURI = request.getRequestURI();
        String requestMethod = request.getMethod();
        log.debug("set up mdc before request to: {}", requestURI);
        MDCUtil.setUpMDC(request);
        if (handler instanceof HandlerMethod) {
            log.info("preHandle: request matched {} - {}", requestMethod, requestURI);
        } else {
            log.warn("preHandle: request to uri:{} not matched any method ", requestURI);
        }
        if (request.getHeader(HttpHeaders.AUTHORIZATION) != null) {
            String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
            User user = authenticationService.decodeTokenWithoutServiceRequest(token);
            log.debug("User decoded Sucessfully : {}",user);
            request.setAttribute(ServletAttribute.USER.getKey(),user);
            if(user != null){
                MDCUtil.addUserId(user.getUserId());
                MDCUtil.addRetailerId(user.getRetailerId());
            }
            log.debug("Authorization token found in request header to {} -{}",requestMethod,requestURI);
        }else{
            log.debug("Authorization token not found in request header to {} -{}",requestMethod,requestURI);
        }
        return true;
    }
}
