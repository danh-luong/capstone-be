package com.dtvc.api.service;

import com.dtvc.api.location.HelmetLocation;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class HelmetService {

    private static final String helmetApi = "http://127.0.0.1:5001/detect_image";

    private final RestTemplate restTemplate;

    public HelmetService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Async
    public CompletableFuture<String> getListHelmetLocation(Map<String, String> imageBase64) throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(helmetApi, imageBase64, String.class);
        String helmetLocations = responseEntity.getBody();
        return CompletableFuture.completedFuture(helmetLocations);
    }
}
