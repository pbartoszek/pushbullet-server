package com.pushserver.infrastructure;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PushBulletRestTemplateConfiguration {

    @Bean
    @Qualifier("pushBullet")
    RestTemplate restTemplate(PushBulletRestTemplateErrorHandler errorHandler) {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
        requestFactory.setOutputStreaming(false);

        RestTemplate restTemplate = new RestTemplate(requestFactory);
        restTemplate.setErrorHandler(errorHandler);
        return restTemplate;
    }

}
