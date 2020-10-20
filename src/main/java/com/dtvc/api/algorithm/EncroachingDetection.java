package com.dtvc.api.algorithm;

import com.dtvc.api.location.LinePoint;
import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.Point;
import core.constants.BusinessConstants;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EncroachingDetection {
    public List<MotorbikeLocation> listEncroaching(List<MotorbikeLocation> motorbikeLocationList,
                                                 LinePoint linePoint, LinePoint distance) throws Exception {
        List<MotorbikeLocation> listResult = new ArrayList<>();
        MotorbikeLocation motorbike;
        // location of marker
        Point upperMarker = new Point(linePoint.getLeft(), linePoint.getTop());
        Point lowerMarker = new Point(linePoint.getRight(), linePoint.getBottom());

        // distance of bounding box
        double targetDistance;
        double calculateDis1, calculateDis2;
        double ratio;

        for (int i = 0; i < motorbikeLocationList.size(); i++) {
            motorbike = motorbikeLocationList.get(i);
            // upper line
            Point leftUpLine = new Point(motorbike.getLeft(), motorbike.getTop());
            Point rightUpLine = new Point(motorbike.getRight(), motorbike.getTop());

            // distance of bounding box
            targetDistance = leftUpLine.calculateDistance(rightUpLine);
            
            // intersection of upper line and marker line
            Point intersect1 = Point.lineLineIntersection(upperMarker, lowerMarker, leftUpLine, rightUpLine);
            if (intersect1.getX() == Double.MAX_VALUE &&
                    intersect1.getY() == Double.MAX_VALUE)
            {
                calculateDis1 = 0;
            } else
                // calculate distance from the left to intersection point
            calculateDis1 = leftUpLine.calculateDistance(intersect1);

            
            // lower line
            Point leftLowLine = new Point(motorbike.getLeft(), motorbike.getBottom());
            Point rightLowLine = new Point(motorbike.getRight(), motorbike.getBottom());

            // intersection of lower line and marker line
            Point intersect2 = Point.lineLineIntersection(upperMarker, lowerMarker, leftLowLine, rightLowLine);

            if (intersect2.getX() == Double.MAX_VALUE &&
                    intersect2.getY() == Double.MAX_VALUE)
            {
                calculateDis2 = 0;
            } else
            // calculate distance from the left to intersection point
            calculateDis2 = leftLowLine.calculateDistance(intersect2);

            ratio = Math.max(calculateDis1, calculateDis2) / targetDistance;
            if (ratio >= BusinessConstants.OverLapRate.thresholdEncroaching) {
                listResult.add(motorbike);
            }
        }
        return listResult;
    }
}
