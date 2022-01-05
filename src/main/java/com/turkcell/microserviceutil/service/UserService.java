package com.turkcell.microserviceutil.service;

import com.turkcell.microserviceutil.enumaration.ServletAttribute;
import com.turkcell.microserviceutil.exception.AuthorizationException;
import com.turkcell.microserviceutil.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Optional;

public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

    public UserService() {
    }

    public static User getUser(boolean throwExceptionIfNull){
        Optional<User> optionalUser;

        try{
            optionalUser = Optional.ofNullable((User) RequestContextHolder.currentRequestAttributes().getAttribute(ServletAttribute.USER.getKey(), 0));
            if(optionalUser.isPresent()){
                return optionalUser.get();
            }else if(throwExceptionIfNull){
                throw new AuthorizationException();
            }
        } catch (Exception e) {
            LOGGER.error("Error while getting user information from RequestContextHolder. throwExceptionIfNull: {}",throwExceptionIfNull,e);
            if(throwExceptionIfNull){
                throw e;
            }else{
                return new User();
            }
        }
        return new User();
    }
}
