package com.dtvc.api.service;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class PersonAndMotorbikeService {

    private static final String helmetApi = "http://127.0.0.1:5000/detect_image";

    private final RestTemplate restTemplate;

    public PersonAndMotorbikeService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<String> getListPersonAndMotorbikeLocation(Map<String, String> imageBase64) throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(helmetApi, imageBase64, String.class);
        String helmetLocations = responseEntity.getBody();
        return CompletableFuture.completedFuture(helmetLocations);
    }
}
