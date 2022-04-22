package com.turkcell.microserviceutil.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 13:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RemoteCallRequest {
    private RemoteRestCallPackage processRequest;
    private RemoteCallPackage compensationRequest;
}

