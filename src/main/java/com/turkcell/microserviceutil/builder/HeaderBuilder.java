package com.turkcell.microserviceutil.builder;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.Collections;

public class HeaderBuilder {
    private final HttpHeaders headers;

    public HeaderBuilder(String token){
        headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    public HttpEntity<?> get() { return new HttpEntity<>("body", this.headers);}
    public HttpEntity<?> post(Object body) { return new HttpEntity<>(body, this.headers);}

}
