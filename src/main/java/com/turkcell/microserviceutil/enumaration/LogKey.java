package com.turkcell.microserviceutil.enumaration;

public enum LogKey {
    REMOTE_ADDRESS("remote-address"),
    REMOTE_PORT("remote-port"),
    REMOTE_HOST("remote-host"),
    FORWARDED_ADDRESS( "x-forwarded-for"),
    USER_ID("user-id"),
    RETALER_ID("retailer-id"),
    HOST_IP ("hots-ip"),
    REQUEST_ID("request-id");

    private final String value;

    LogKey(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
