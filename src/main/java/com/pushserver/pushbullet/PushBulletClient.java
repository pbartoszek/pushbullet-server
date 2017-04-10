package com.pushserver.pushbullet;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Component
public class PushBulletClient {
    private final String ACCESS_TOKEN_HEADER = "Access-Token";
    private final String pushBulletApiUrl;
    private final RestTemplate restTemplate;
    private ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<Map<String, Object>>() {
    };

    public PushBulletClient(@Value("${push.bullet.url}") String pushBulletApiUrl,
                            @Qualifier("pushBullet") RestTemplate restTemplate) {
        this.pushBulletApiUrl = pushBulletApiUrl;
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> send(String endpoint, Object request, String accessToken) {
        HttpHeaders headers = createRequestHeaders(accessToken);
        HttpEntity<?> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Map<String, Object>> responseEntity = sendPush(entity, endpoint);
        return responseEntity.getBody();
    }

    private ResponseEntity<Map<String, Object>> sendPush(HttpEntity<?> entity, String endpoint) {
        return restTemplate.exchange(pushBulletApiUrl + endpoint,
            HttpMethod.POST, entity, responseType, Collections.emptyMap());
    }

    private HttpHeaders createRequestHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(ACCESS_TOKEN_HEADER, accessToken);
        return headers;
    }

}
