package com.turkcell.microserviceutil.enumaration;

public enum ServletAttribute {
    USER("user"), START_TIME("request-enter-time");

    private final String key;

    ServletAttribute(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
