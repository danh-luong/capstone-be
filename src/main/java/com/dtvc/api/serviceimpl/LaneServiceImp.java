package com.dtvc.api.serviceimpl;

import com.dtvc.api.service.LaneService;
import org.springframework.stereotype.Service;

@Service
public class LaneServiceImp implements LaneService {

    @Override
    public int calculateDistance(int[] line, int[] lane) {
        int top = lane[3];
        int bottom = line[2];
        if (lane[3] < lane[2]) {
            top = lane[2];
        }
        if (line[2] > line[3]) {
            bottom = line[3];
        }
        int distance = bottom - top;
        return distance;
    }
}
