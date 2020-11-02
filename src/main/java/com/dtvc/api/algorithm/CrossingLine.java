package com.dtvc.api.algorithm;

import com.dtvc.api.location.LinePoint;
import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.Point;
import core.constants.BusinessConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrossingLine {
    public List<MotorbikeLocation> listCrossing(List<MotorbikeLocation> motorbikeLocationList,
                                                   LinePoint linePoint) {
        List<MotorbikeLocation> listResult = new ArrayList<>();
        MotorbikeLocation motorbike;
        for (int i = 0; i < motorbikeLocationList.size(); i++) {
            motorbike = motorbikeLocationList.get(i);
            Point upperPoint = new Point(motorbike.getLeft(), motorbike.getTop());
            Point lowerPoint = new Point(motorbike.getRight(), motorbike.getBottom());

            double distanceRect = upperPoint.calculateDistance(lowerPoint);
            Point intersectPoint = new Point(motorbike.getLeft(), linePoint.getTop());

            double crossingDistance = upperPoint.calculateDistance(intersectPoint);
            double ratio = 1.0 * crossingDistance / distanceRect;

            if (ratio > BusinessConstants.OverLapRate.thresholdCrossing) {
                listResult.add(motorbike);
            }
        }

        return listResult;
    }
}
