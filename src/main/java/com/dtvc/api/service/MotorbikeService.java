package com.dtvc.api.service;

import com.dtvc.api.location.MotorbikeLocation;

import java.util.List;

public interface MotorbikeService {

    int detectPassingRedLight(List<MotorbikeLocation> list, int[] line, float rate, int laneDistance);
}
