package com.turkcell.microserviceutil.model.request;


import lombok.Data;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 16:36
 */
@Data
public abstract class RemoteCallPackage {
    private String serviceName;

}
