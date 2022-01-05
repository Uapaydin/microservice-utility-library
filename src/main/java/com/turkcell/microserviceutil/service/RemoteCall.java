package com.turkcell.microserviceutil.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.turkcell.microserviceutil.dto.RemoteCallDTO;
import com.turkcell.microserviceutil.enumaration.AuthorizationType;
import com.turkcell.microserviceutil.enumaration.ReturnType;
import com.turkcell.microserviceutil.exception.RemoteCallException;
import com.turkcell.microserviceutil.exception.AuthorizationException;
import com.turkcell.microserviceutil.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.StopWatch;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class RemoteCall {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCall.class);

    private static RestTemplate restTemplate;
    private static ObjectMapper objectMapper;

    static {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public RemoteCall() {
    }

    public static <T> T callForObject(String url, HttpMethod httpMethod, Class<T> type, AuthorizationType authorizationType,
                                      Object body) {
        RemoteCallDTO<T> remoteCallDTO = makeCall(url, httpMethod, authorizationType, body, null);

        return objectMapper.convertValue(remoteCallDTO.getContent(), type);
    }

    public static <T> List<T> callForList(String url, HttpMethod httpMethod, Class<T> type, AuthorizationType authorizationType,
                                          Object body) {
        RemoteCallDTO<T> remoteCallDTO = makeCall(url, httpMethod, authorizationType, body, null);

        JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);

        return objectMapper.convertValue(remoteCallDTO.getContent(), listType);
    }

    public static <K, V> Map<K, V> callForMap(String url, HttpMethod httpMethod, Class<K> typeKey, Class<V> typeValue,
                                              AuthorizationType authorizationType, Object body) {
        RemoteCallDTO<Map<K, V>> remoteCallDTO = makeCall(url, httpMethod, authorizationType, body, null);

        JavaType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, typeKey, typeValue);

        return objectMapper.convertValue(remoteCallDTO.getContent(), mapType);
    }

    public static <T> T callForObject(String url, HttpMethod httpMethod, Class<T> type, AuthorizationType authorizationType) {
        RemoteCallDTO<T> remoteCallDTO = makeCall(url, httpMethod, authorizationType, null, null);

        return objectMapper.convertValue(remoteCallDTO.getContent(), type);
    }

    public static <T> List<T> callForList(String url, HttpMethod httpMethod, Class<T> type, AuthorizationType authorizationType) {
        RemoteCallDTO<T> remoteCallDTO = makeCall(url, httpMethod, authorizationType, null, null);

        JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);

        return objectMapper.convertValue(remoteCallDTO.getContent(), listType);
    }

    public static <K, V> Map<K, V> callForMap(String url, HttpMethod httpMethod, Class<K> typeKey, Class<V> typeValue,
                                              AuthorizationType authorizationType) {
        RemoteCallDTO<Map<K, V>> remoteCallDTO = makeCall(url, httpMethod, authorizationType, null, null);

        JavaType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, typeKey, typeValue);

        return objectMapper.convertValue(remoteCallDTO.getContent(), mapType);
    }

    //callFor methods with jwt injection
    public static <T> T callForObject(String url, HttpMethod httpMethod, Class<T> type, AuthorizationType authorizationType,
                                      String jwt) {
        RemoteCallDTO<T> remoteCallDTO = makeCall(url, httpMethod, authorizationType, null, jwt);

        return objectMapper.convertValue(remoteCallDTO.getContent(), type);
    }

    public static <T> List<T> callForList(String url, HttpMethod httpMethod, Class<T> type, AuthorizationType authorizationType,
                                          String jwt) {
        RemoteCallDTO<T> remoteCallDTO = makeCall(url, httpMethod, authorizationType, null, jwt);

        JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);

        return objectMapper.convertValue(remoteCallDTO.getContent(), listType);
    }

    public static <K, V> Map<K, V> callForMap(String url, HttpMethod httpMethod, Class<K> typeKey, Class<V> typeValue,
                                              AuthorizationType authorizationType, String jwt) {
        RemoteCallDTO<Map<K, V>> remoteCallDTO = makeCall(url, httpMethod, authorizationType, null, jwt);

        JavaType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, typeKey, typeValue);

        return objectMapper.convertValue(remoteCallDTO.getContent(), mapType);
    }

    public static <T> T callForObject(String url, HttpMethod httpMethod, Class<T> type, AuthorizationType authorizationType,
                                      Object body, String jwt) {
        RemoteCallDTO<T> remoteCallDTO = makeCall(url, httpMethod, authorizationType, body, jwt);

        return objectMapper.convertValue(remoteCallDTO.getContent(), type);
    }

    public static <T> List<T> callForList(String url, HttpMethod httpMethod, Class<T> type, AuthorizationType authorizationType,
                                          Object body, String jwt) {
        RemoteCallDTO<T> remoteCallDTO = makeCall(url, httpMethod, authorizationType, body, jwt);

        JavaType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, type);

        return objectMapper.convertValue(remoteCallDTO.getContent(), listType);
    }

    public static <K, V> Map<K, V> callForMap(String url, HttpMethod httpMethod, Class<K> typeKey, Class<V> typeValue,
                                              AuthorizationType authorizationType, Object body, String jwt) {
        RemoteCallDTO<Map<K, V>> remoteCallDTO = makeCall(url, httpMethod, authorizationType, body, jwt);

        JavaType mapType = objectMapper.getTypeFactory().constructMapType(Map.class, typeKey, typeValue);

        return objectMapper.convertValue(remoteCallDTO.getContent(), mapType);
    }


    @SuppressWarnings("rawtypes")
    private static RemoteCallDTO makeCall(String url, HttpMethod httpMethod, AuthorizationType authorizationType,
                                             @Nullable Object body, @Nullable String jwt) {
        LOGGER.info("Make {} request to url: {}", httpMethod.name(), url);

        HttpEntity<Object> entity = createHeader(authorizationType, body, jwt);

        final StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ResponseEntity<RemoteCallDTO> responseEntity = restTemplate.exchange(url, httpMethod, entity, RemoteCallDTO.class);
        stopWatch.stop();

        RemoteCallDTO remoteCallDTO = Optional.ofNullable(responseEntity.getBody())
                .orElseThrow(() -> new RemoteCallException("Error occurred during service call to: " + url));

        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("{} request to url: {} | Authorization type: {} | completed in {} ms | response: {}",
                    httpMethod, url, authorizationType, stopWatch.getTotalTimeMillis(), remoteCallDTO);
        } else {
            LOGGER.info("{} request to url: {} | Authorization type: {} | completed in {} ms",
                    httpMethod, url, authorizationType, stopWatch.getTotalTimeMillis());
        }

        if (remoteCallDTO.getReturnCode() != ReturnType.SUCCESS.getCode()) {
            throw new RemoteCallException(remoteCallDTO.getError());
        }

        return remoteCallDTO;
    }

    private static HttpEntity<Object> createHeader(AuthorizationType authorizationType, Object body, @Nullable String jwt) {
        HttpHeaders headers = new HttpHeaders();

        if (authorizationType == AuthorizationType.USER) {
            User user = UserService.getUser(true);
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + user.getToken());
            headers.set(HttpHeaders.ACCEPT_LANGUAGE, LocaleContextHolder.getLocale().getLanguage());
        } else if (authorizationType == AuthorizationType.SYSTEM) {
            if (jwt == null || jwt.isEmpty()) {
                LOGGER.error("While using AuthorizationType.SYSTEM, you can not use empty JWT");
                throw new AuthorizationException();
            }
            headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
        } else {
            LOGGER.info("AuthorizationType: {} | Request Header will be empty", authorizationType);
        }

        return new HttpEntity<>(body, headers);
    }
}
