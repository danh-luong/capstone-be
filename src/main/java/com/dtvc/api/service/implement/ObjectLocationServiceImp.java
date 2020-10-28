package com.dtvc.api.service.implement;

import com.dtvc.api.service.ObjectLocationService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ObjectLocationServiceImp implements ObjectLocationService {

    private static final String api = "http://127.0.0.1:5000/detect_image";

    private final RestTemplate restTemplate;

    public ObjectLocationServiceImp(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public String getListObjectLocation(Map<String, String> imageBase64) throws Exception {
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(api, imageBase64, String.class);
        String listObject = responseEntity.getBody();
        System.out.println(listObject);
        return listObject;
    }
}
