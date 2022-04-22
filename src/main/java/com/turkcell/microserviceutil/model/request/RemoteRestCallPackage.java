package com.turkcell.microserviceutil.model.request;

import com.turkcell.microserviceutil.enumaration.AuthorizationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;

/**
 * @author Utku APAYDIN
 * @created 22/04/2022 - 13:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RemoteRestCallPackage extends RemoteCallPackage {
    private String url;
    Object body;
    private HttpMethod httpMethod;
    AuthorizationType authorizationType;
    MultiValueMap<String, String> headers;
}