package com.dtvc.api.controller;

import com.dtvc.api.location.HelmetLocation;
import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.PersonLocation;
import com.dtvc.api.service.HelmetService;
import com.dtvc.api.service.PersonAndMotorbikeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/detect")
public class HandleDetectObjectsController {

    @Autowired
    private HelmetService helmetService;

    @Autowired
    private PersonAndMotorbikeService personAndMotorbikeService;

    @PostMapping("/object")
    public List<HelmetLocation> callDetectObjectsApi(@RequestBody Map<String, String> imageBase64) {
        long start = System.currentTimeMillis();
        CompletableFuture<String> helmetLocationListString = null;
//        CompletableFuture<String> personAndMotorbikeListString = null;
        try {
            helmetLocationListString = helmetService.getListHelmetLocation(imageBase64);
            CompletableFuture.allOf(helmetLocationListString).join();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            List<HelmetLocation> helmetLocationList = objectMapper.readValue(helmetLocationListString.get(), new TypeReference<List<HelmetLocation>>(){});
//            personAndMotorbikeListString = personAndMotorbikeService.getListPersonAndMotorbikeLocation(imageBase64);
//            CompletableFuture.allOf(personAndMotorbikeListString).join();
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//            Map<String, Object> jsonMap = objectMapper.readValue(personAndMotorbikeListString.get(),
//                    new TypeReference<Map<String, Object>>(){});
//            Iterator jsonMapIterator = jsonMap.entrySet().iterator();
//            List<PersonLocation> personLocationList = new ArrayList<>();
//            List<MotorbikeLocation> motorbikeLocationList = new ArrayList<>();
//            while (jsonMapIterator.hasNext()) {
//                Map.Entry pair = (Map.Entry) jsonMapIterator.next();
//                if (String.valueOf(pair.getKey()).contains("person")) {
//                    personLocationList.add(objectMapper.convertValue(pair.getValue(), PersonLocation.class));
//                }
//                if (String.valueOf(pair.getKey()).contains("motorbike")) {
//                    motorbikeLocationList.add(objectMapper.convertValue(pair.getValue(), MotorbikeLocation.class));
//                }
//            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
        return null;
    }

    @GetMapping("/object")
    public String helloWorld() {
        return "hello world";
    }
}
