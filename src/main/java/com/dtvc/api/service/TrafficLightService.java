package com.dtvc.api.service;

import com.dtvc.api.location.TrafficLight;

import java.util.List;

public interface TrafficLightService {

    boolean isRed(List<TrafficLight> list);
}
