package com.turkcell.microserviceutil.enumaration;

public enum ReturnType {
    SUCCESS(0,"The operation succeeded."),
    FAILURE(-1,"An error occured");

    int code;
    String message;

    ReturnType(int code, String message){
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
