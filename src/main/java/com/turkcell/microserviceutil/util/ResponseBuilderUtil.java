package com.turkcell.microserviceutil.util;

import com.turkcell.microserviceutil.builder.ResponseBuilder;
import com.turkcell.microserviceutil.enumaration.ReturnType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

@Configuration
public class ResponseBuilderUtil
{
    @Bean
    public ResponseBuilder responseBuilder(){
        return new ResponseBuilder(HttpStatus.OK, ReturnType.SUCCESS);
    }
}
