package com.dtvc.api.controller;

import com.dtvc.api.algorithm.WearingHelmetAlgorithm;
import com.dtvc.api.location.HelmetLocation;
import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.PersonLocation;
import com.dtvc.api.location.TrafficLight;
import com.dtvc.api.service.*;
import com.dtvc.api.util.Constants;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


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
    private ObjectLocationService objectLocationService;

    @Autowired
    private PersonAndMotorbikeService personAndMotorbikeService;

    @PostMapping("/object")
    public String callDetectObjectsApi(@RequestBody Map<String, String> imageBase64) {
        long start = System.currentTimeMillis();
        String listObject = null;
        String result = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
            listObject = objectLocationService.getListObjectLocation(imageBase64);
            Map<String, Object> jsonMap = objectMapper.readValue(listObject,
                    new TypeReference<Map<String, Object>>() {
                    });
            Iterator jsonMapIterator = jsonMap.entrySet().iterator();
            List<PersonLocation> personLocationList = new ArrayList<>();
            List<TrafficLight> lights = new ArrayList<>();
            List<HelmetLocation> helmetLocationList = new ArrayList<>();
            List<MotorbikeLocation> motorbikeLocationList = new ArrayList<>();
            while (jsonMapIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) jsonMapIterator.next();
                if (String.valueOf(pair.getKey()).contains("person")) {
                    personLocationList.add(objectMapper.convertValue(pair.getValue(), PersonLocation.class));
                } else if (String.valueOf(pair.getKey()).contains("motorbike")) {
                    motorbikeLocationList.add(objectMapper.convertValue(pair.getValue(), MotorbikeLocation.class));
                } else if (String.valueOf(pair.getKey()).contains("traffic-light")) {
                    lights.add(objectMapper.convertValue(pair.getValue(), TrafficLight.class));
                } else if (String.valueOf(pair.getKey()).contains("helmet")) {
                    helmetLocationList.add(objectMapper.convertValue(pair.getValue(), HelmetLocation.class));
                }
            }
            boolean isRed = trafficLightService.isRed(lights);
            if (isRed) {
//              The position of crossing line
//              [x1, x2, y1, y2] line
                int[] line = {462, 1000, 468, 450};
                int[] lane = {462, 1000, 200, 230};
//                calculate distance from line to lane
                int laneDistance = laneService.calculateDistance(line, lane);
                System.out.println("lane: " + laneDistance);
                int count = motorbikeService.detectPassingRedLight(motorbikeLocationList, line, Constants.LANE_RATE, laneDistance);
                if (count > 0) {
                    result = "There are " + count + " cases of red light violation";
                }
                //Match PersonLocation and MotorbikeLocation
                Map<PersonLocation, MotorbikeLocation> personLocationMotorbikeLocationMap = new WearingHelmetAlgorithm().isRelatedPersonAndMotorbike(motorbikeLocationList, personLocationList);

                //Get all PersonLocation base on Motorbike
                List<PersonLocation> matchedPersonMotorbike = new ArrayList<PersonLocation>(personLocationMotorbikeLocationMap.keySet());

                //Match map HelmetLocation and MotorbikeLocation with HelmetLocation
                Map<PersonLocation, HelmetLocation> matchedPersonWithHelmetMap = new WearingHelmetAlgorithm().isRelatedPersonAndHelmet(matchedPersonMotorbike, helmetLocationList);

                //Get List PersonLocation and MotorbikeLocation base on HelmetLocation
                List<PersonLocation> matchedPersonHelmetList = new ArrayList<PersonLocation>(matchedPersonWithHelmetMap.keySet());

                //Check isWearingHelmet or Not
                Map<PersonLocation, MotorbikeLocation> notWearingHelmet = new HashMap<>();
                int countNotHelmet = 0;
                for(PersonLocation entry: matchedPersonWithHelmetMap.keySet()){
                    System.out.println("4 left: " + entry.getLeft() + " | right: " + entry.getRight()
                            + " | top: " + entry.getTop() + " | bottom: " + entry.getBottom());
                    System.out.println("5 left: " + matchedPersonWithHelmetMap.get(entry).getLeft() + " | right: " + matchedPersonWithHelmetMap.get(entry).getRight()
                            + " | top: " + matchedPersonWithHelmetMap.get(entry).getTop() + " | bottom: " + matchedPersonWithHelmetMap.get(entry).getBottom());

                }
                System.out.println("size:"+ matchedPersonWithHelmetMap.size());
                System.out.println("size 2:"+ matchedPersonMotorbike.size());
                if (matchedPersonMotorbike.size() != matchedPersonHelmetList.size()) {
                    System.out.println("aaaaaaa");
                    for (int i = 0; i < matchedPersonMotorbike.size(); i++) {
                        if (!matchedPersonWithHelmetMap.containsKey(matchedPersonMotorbike.get(i))) {
                            notWearingHelmet.put(matchedPersonMotorbike.get(i),
                                    personLocationMotorbikeLocationMap.get(matchedPersonMotorbike.get(i)));
                            countNotHelmet++;
                            MotorbikeLocation motorbike = personLocationMotorbikeLocationMap.get(matchedPersonMotorbike.get(i));
                            System.out.println("2 left: " + motorbike.getLeft() + " | right: " + motorbike.getRight()
                                    + " | top: " + motorbike.getTop() + " | bottom: " + motorbike.getBottom());
                        }
                    }
                }
                if (countNotHelmet > 0) {
                    result += "\nThere are " + countNotHelmet + " cases of violation without wearing a helmet";
                }
            }
            if (result.isEmpty()) {
                result = "Not violation";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
        return result;
    }

//    }@PostMapping("/object")
//    public String callDetectObjectsApi(@RequestBody Map<String, String> imageBase64) {
//        long start = System.currentTimeMillis();
//        CompletableFuture<String> helmetLocationListString = null;
//        CompletableFuture<String> personAndMotorbikeListString = null;
//        String result = "";
//        try {
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
//            //call async
//            helmetLocationListString = helmetService.getListHelmetLocation(imageBase64);
//            personAndMotorbikeListString = personAndMotorbikeService.getListPersonAndMotorbikeLocation(imageBase64);
//            CompletableFuture.allOf(personAndMotorbikeListString, helmetLocationListString).join();
//
//            //parse Json to Object
//            List<HelmetLocation> helmetLocationList = objectMapper.readValue(helmetLocationListString.get(), new TypeReference<List<HelmetLocation>>() {
//            });
//            Map<String, Object> jsonMap = objectMapper.readValue(personAndMotorbikeListString.get(),
//                    new TypeReference<Map<String, Object>>() {
//                    });
//            Iterator jsonMapIterator = jsonMap.entrySet().iterator();
//            List<PersonLocation> personLocationList = new ArrayList<>();
//            List<MotorbikeLocation> motorbikeLocationList = new ArrayList<>();
//            List<TrafficLight> lights = new ArrayList<>();
//            while (jsonMapIterator.hasNext()) {
//                Map.Entry pair = (Map.Entry) jsonMapIterator.next();
//                if (String.valueOf(pair.getKey()).contains("person")) {
//                    personLocationList.add(objectMapper.convertValue(pair.getValue(), PersonLocation.class));
//                } else if (String.valueOf(pair.getKey()).contains("motorbike")) {
//                    motorbikeLocationList.add(objectMapper.convertValue(pair.getValue(), MotorbikeLocation.class));
//                } else if (String.valueOf(pair.getKey()).contains("traffic-light")) {
//                    lights.add(objectMapper.convertValue(pair.getValue(), TrafficLight.class));
//                }
//            }
//            boolean isRed = trafficLightService.isRed(lights);
//            if (isRed) {
////              The position of crossing line
////              [x1, x2, y1, y2] line
//                int[] line = {462, 1000, 468, 450};
//                int[] lane = {462, 1000, 200, 230};
////                calculate distance from line to lane
//                int laneDistance = laneService.calculateDistance(line, lane);
//                System.out.println("lane: " + laneDistance);
//                int count = motorbikeService.detectPassingRedLight(motorbikeLocationList, line, Constants.LANE_RATE, laneDistance);
//                if (count > 0) {
//                    result = "There are " + count + " cases of red light violation";
//                }
//                //Match PersonLocation and MotorbikeLocation
//                Map<PersonLocation, MotorbikeLocation> personLocationMotorbikeLocationMap = new WearingHelmetAlgorithm().isRelatedPersonAndMotorbike(motorbikeLocationList, personLocationList);
//
//                //Get all PersonLocation base on Motorbike
//                List<PersonLocation> matchedPersonMotorbike = new ArrayList<PersonLocation>(personLocationMotorbikeLocationMap.keySet());
//
//                //Match map HelmetLocation and MotorbikeLocation with HelmetLocation
//                Map<PersonLocation, HelmetLocation> matchedPersonWithHelmetMap = new WearingHelmetAlgorithm().isRelatedPersonAndHelmet(matchedPersonMotorbike, helmetLocationList);
//
//                //Get List PersonLocation and MotorbikeLocation base on HelmetLocation
//                List<PersonLocation> matchedPersonHelmetList = new ArrayList<PersonLocation>(matchedPersonWithHelmetMap.keySet());
//
//                //Check isWearingHelmet or Not
//                Map<PersonLocation, MotorbikeLocation> notWearingHelmet = new HashMap<>();
//                int countNotHelmet = 0;
//                if (matchedPersonMotorbike.size() != matchedPersonHelmetList.size()) {
//                    for (int i = 0; i < matchedPersonMotorbike.size(); i++) {
//                        if (!matchedPersonWithHelmetMap.containsKey(matchedPersonMotorbike.get(i))) {
//                            notWearingHelmet.put(matchedPersonMotorbike.get(i),
//                                    personLocationMotorbikeLocationMap.get(matchedPersonMotorbike.get(i)));
//                            countNotHelmet++;
//                            MotorbikeLocation motorbike = personLocationMotorbikeLocationMap.get(matchedPersonMotorbike.get(i));
//                            System.out.println("left: " + motorbike.getLeft() + " | right: " + motorbike.getRight()
//                                    + " | top: " + motorbike.getTop() + " | bottom: " + motorbike.getBottom());
//                        }
//                    }
//                }
//                if (countNotHelmet > 0) {
//                    result += "\nThere are " + countNotHelmet + " cases of violation without wearing a helmet";
//                }
//            }
//            if (result.isEmpty()) {
//                result = "Not violation";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start));
//        return result;
//    }


}
