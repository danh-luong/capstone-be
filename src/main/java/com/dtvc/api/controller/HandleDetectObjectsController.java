package com.dtvc.api.controller;

import com.dtvc.api.algorithm.WearingHelmetAlgorithm;
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

import java.util.*;
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
        CompletableFuture<String> personAndMotorbikeListString = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            //call async
            helmetLocationListString = helmetService.getListHelmetLocation(imageBase64);
            personAndMotorbikeListString = personAndMotorbikeService.getListPersonAndMotorbikeLocation(imageBase64);
            CompletableFuture.allOf(personAndMotorbikeListString, helmetLocationListString).join();

            //parse Json to Object
            List<HelmetLocation> helmetLocationList = objectMapper.readValue(helmetLocationListString.get(), new TypeReference<List<HelmetLocation>>(){});
            Map<String, Object> jsonMap = objectMapper.readValue(personAndMotorbikeListString.get(),
                    new TypeReference<Map<String, Object>>(){});
            Iterator jsonMapIterator = jsonMap.entrySet().iterator();
            List<PersonLocation> personLocationList = new ArrayList<>();
            List<MotorbikeLocation> motorbikeLocationList = new ArrayList<>();
            while (jsonMapIterator.hasNext()) {
                Map.Entry pair = (Map.Entry) jsonMapIterator.next();
                if (String.valueOf(pair.getKey()).contains("person")) {
                    personLocationList.add(objectMapper.convertValue(pair.getValue(), PersonLocation.class));
                }
                if (String.valueOf(pair.getKey()).contains("motorbike")) {
                    motorbikeLocationList.add(objectMapper.convertValue(pair.getValue(), MotorbikeLocation.class));
                }
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
            if (matchedPersonMotorbike.size() != matchedPersonHelmetList.size()) {
                for (int i = 0; i < matchedPersonMotorbike.size(); i++) {
                    if (!matchedPersonWithHelmetMap.containsKey(matchedPersonMotorbike.get(i))) {
                        notWearingHelmet.put(matchedPersonMotorbike.get(i),
                                personLocationMotorbikeLocationMap.get(matchedPersonMotorbike.get(i)));
                    }
                }
            }

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
