package com.dtvc.api.service.implement;

import com.dtvc.api.location.TrafficLight;
import com.dtvc.api.service.TrafficLightService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrafficLightServiceImp implements TrafficLightService {

    @Override
    public boolean isRed(List<TrafficLight> list) {
        boolean result = false;
        for (TrafficLight light : list) {
            if (light.isRed()) {
                result = true;
                break;
            }
        }
        return result;
    }
}
