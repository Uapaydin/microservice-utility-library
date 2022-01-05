package com.turkcell.microserviceutil.enumaration;

import org.springframework.boot.logging.LogLevel;

public enum LoggableType {
    CONTROLLER(LogLevel.INFO),
    SERVICE(LogLevel.DEBUG),
    REPOSITORY(LogLevel.DEBUG),
    COMPONENT(LogLevel.DEBUG),
    OTHER(LogLevel.DEBUG);

    private final LogLevel logLevel;

    LoggableType(LogLevel loggingLevel) {
        this.logLevel = loggingLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }


}
