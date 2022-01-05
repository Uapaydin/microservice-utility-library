package com.turkcell.microserviceutil.annotation;


import com.turkcell.microserviceutil.enumaration.LoggableType;
import com.turkcell.microserviceutil.enumaration.LoggingDirection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loggable {
    LoggableType type();
    LoggingDirection direction();
}
