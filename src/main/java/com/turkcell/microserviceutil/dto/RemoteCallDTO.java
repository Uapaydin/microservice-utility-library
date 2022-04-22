package com.turkcell.microserviceutil.dto;

import lombok.Data;

@Data
public class RemoteCallDTO<T> {

    private  int returnCode;
    private String returnMessage;
    private String error;
    private Boolean isPaginated;
    private T content;

}
