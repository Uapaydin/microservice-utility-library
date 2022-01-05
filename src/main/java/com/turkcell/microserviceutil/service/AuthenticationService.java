package com.turkcell.microserviceutil.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.microserviceutil.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

@Service
public class AuthenticationService {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthenticationService.class);

//    @Value("${uri.authenticaiton.decode}")
//    private String tokenDecoderUri;
//    @Value("${uri.authenticaiton.service.token}")
//    private String tokenServiceUri;
    @Value("${spring.application.name}")
    private String applicationName;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectmapper;


    public AuthenticationService() {
        this.restTemplate = new RestTemplate();
        this.objectmapper = new ObjectMapper();
        objectmapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectmapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
    }

//    public User decodeToken(String token){
//        Map<String,String> request = new HashMap<>();
//        request.put("jwt",token);
//        LOGGER.info("Decode token from uri: {}",tokenDecoderUri);
//        return restTemplate.postForObject(tokenDecoderUri,request,User.class);
//    }

    public User decodeTokenWithoutServiceRequest(String token){
        User user = null;
        String[] splitToken = token.split("\\.");
        String base64EncodedBody = splitToken[1];

        Base64.Decoder decoder = Base64.getUrlDecoder();

        String body = new String(decoder.decode(base64EncodedBody));

        try{
            user = objectmapper.readValue(body,User.class);
            user.setToken(token);
        } catch (IOException e){
            LOGGER.error("Error while decoding token: ", e);
        }
        return user;
    }

//    public String generateTokenForServices(String tenantId){
//        if( tenantId == null || tenantId.isEmpty()){
//            return (String) restTemplate.getForObject(tokenServiceUri+"/"+applicationName,Map.class).get("content");
//        }else{
//            return (String) restTemplate.getForObject(tokenServiceUri+"/"+applicationName+"/"+tenantId,Map.class).get("content");
//        }
//    }
}
