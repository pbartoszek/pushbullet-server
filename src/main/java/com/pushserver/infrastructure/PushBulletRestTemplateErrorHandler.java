package com.pushserver.infrastructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pushserver.pushbullet.PushBulletException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.util.Map;

@Component
public class PushBulletRestTemplateErrorHandler implements ResponseErrorHandler {
    private static final String INTERNAL_PUSHBULLET_ERROR = "internal_pushbullet_error";

    private final ObjectMapper objectMapper;

    public PushBulletRestTemplateErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode() != HttpStatus.OK;
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        PushBulletException pushBulletException;
        try {
            pushBulletException = createPusbBulletExcpetion(response);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PushBulletException(null, INTERNAL_PUSHBULLET_ERROR, response.getStatusCode());
        }

        throw pushBulletException;
    }

    private PushBulletException createPusbBulletExcpetion(ClientHttpResponse response) throws IOException {
        Map<String, Object> responseMap = objectMapper.readValue(response.getBody(), Map.class);
        Map<String, Object> errorMap = (Map<String, Object>) responseMap.get("error");
        String errorCode = (String) errorMap.getOrDefault("code", INTERNAL_PUSHBULLET_ERROR);
        String errorMessage = (String) errorMap.get("message");
        return new PushBulletException(errorMessage, errorCode, response.getStatusCode());
    }
}
