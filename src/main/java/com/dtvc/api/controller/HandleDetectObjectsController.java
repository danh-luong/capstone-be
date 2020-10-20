package com.dtvc.api.controller;

import com.dtvc.api.location.HelmetLocation;
import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.PersonLocation;
import com.dtvc.api.location.TrafficLight;
import com.dtvc.api.service.HelmetService;
import com.dtvc.api.service.LaneService;
import com.dtvc.api.service.MotorbikeService;
import com.dtvc.api.service.TrafficLightService;
import com.dtvc.api.service.PersonAndMotorbikeService;
import com.dtvc.api.util.Constants;
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
    private TrafficLightService trafficLightService;

    @Autowired
    private LaneService laneService;

    @Autowired
    private MotorbikeService motorbikeService;

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
            List<HelmetLocation> helmetLocationList = objectMapper.readValue(helmetLocationListString.get(), new TypeReference<List<HelmetLocation>>() {
            });
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

    @GetMapping("/objectV2")
    public String callDetectObjectsApiV2(@RequestBody Map<String, String> imageBase64) {
        String result = "Not violated";
        CompletableFuture<String> personAndMotorbikeListString = null;
        try {
            personAndMotorbikeListString = personAndMotorbikeService.getListPersonAndMotorbikeLocation(imageBase64);
            CompletableFuture.allOf(personAndMotorbikeListString).join();
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            Map<String, Object> jsonMap = objectMapper.readValue(personAndMotorbikeListString.get(),
                    new TypeReference<Map<String, Object>>() {
                    });
            Iterator jsonMapIterator = jsonMap.entrySet().iterator();
            List<PersonLocation> persons = new ArrayList<>();
            List<MotorbikeLocation> motorbikes = new ArrayList<>();
            List<TrafficLight> lights = new ArrayList<>();
            while (jsonMapIterator.hasNext()) {
                Map.Entry entry = (Map.Entry) jsonMapIterator.next();
                if (String.valueOf(entry.getKey()).contains("person")) {
                    persons.add(objectMapper.convertValue(entry.getValue(), PersonLocation.class));
                } else if (String.valueOf(entry.getKey()).contains("motorbike")) {
                    motorbikes.add(objectMapper.convertValue(entry.getValue(), MotorbikeLocation.class));
                } else if (String.valueOf(entry.getKey()).contains("traffic-light")) {
                    lights.add(objectMapper.convertValue(entry.getValue(), TrafficLight.class));
                }
            }
            boolean isRed = trafficLightService.isRed(lights);
            if (isRed) {
//              The position of crossing line
//              [x1, x2, y1, y2] line
                int[] line = {18, 303, 188, 192};
                int[] lane = {18, 303, 100, 132};
//                calculate distance from line to lane
                int laneDistance = laneService.calculateDistance(line, lane);
                System.out.println("lane: "+ laneDistance);
                int count = motorbikeService.detectPassingRedLight(motorbikes, line, Constants.LANE_RATE, laneDistance);
                if (count > 0) {
                    result = "There are " + count + " red light passing violations";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
