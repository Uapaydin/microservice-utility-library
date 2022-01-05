package com.turkcell.microserviceutil.config;

import com.turkcell.microserviceutil.intercepter.GeneralInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final GeneralInterceptor generalInterceptor;

    public WebMvcConfig(GeneralInterceptor generalInterceptor) {
        this.generalInterceptor = generalInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(generalInterceptor);
    }
}
