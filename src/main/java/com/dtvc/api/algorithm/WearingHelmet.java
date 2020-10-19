package com.dtvc.api.algorithm;
import com.dtvc.api.location.HelmetLocation;
import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.PersonLocation;
import core.constants.BusinessConstants;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WearingHelmet {
    public Map<PersonLocation, MotorbikeLocation> isRelatedPersonAndMotorbike(List<MotorbikeLocation> motorbikeLocationList,
                                                                              List<PersonLocation> personLocationList) throws Exception {
        Map<PersonLocation, MotorbikeLocation> listMap = new HashMap<>();
        MotorbikeLocation motorbike;
        PersonLocation person;
        for (int i = 0; i < motorbikeLocationList.size(); i++) {
            motorbike = motorbikeLocationList.get(i);
            for (int j = 0; j < personLocationList.size(); j++) {
                person = personLocationList.get(j);
                if (motorbike.calculateOverlapRate(person) >= BusinessConstants.OverLapRate.thresholdPersonMotorbike) {
                    listMap.put(person, motorbike);
                }
            }
        }
        return listMap;
    }
    public Map<PersonLocation, HelmetLocation> isRelatedPersonAndHelmet(List<PersonLocation> personLocationList,
                                                                        List<HelmetLocation> helmetLocationList) throws Exception {
        Map<PersonLocation, HelmetLocation> listMap = new HashMap<>();
        HelmetLocation helmet;
        PersonLocation person;
        for (int i = 0; i < personLocationList.size(); i++) {
            person = personLocationList.get(i);
            for (int j = 0; j < helmetLocationList.size(); j++) {
                helmet = helmetLocationList.get(j);
                if (helmet.calculateOverlapRate(person) >= BusinessConstants.OverLapRate.thresholdHelmetPerson) {
                    listMap.put(person, helmet);
                }
            }
        }
        return listMap;
    }
}
