package com.dtvc.api.service.implement;

import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.service.MotorbikeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotorbikeServiceImp implements MotorbikeService {

    //    [left, right, top, bottom] moto
    //    [x1, x2, y1, y2] line
    @Override
    public int detectPassingRedLight(List<MotorbikeLocation> list, int[] line, float acceptanceRate, int laneDistance) {
        int count = 0;
        for (MotorbikeLocation motorbike : list) {
//            System.out.println("left: " + motorbike.getLeft() + " | right: " + motorbike.getRight()
//                    + " | top: " + motorbike.getTop() + " | bottom: " + motorbike.getBottom());
//            System.out.println("x: "+ line[0] +" | x: "+ line[1] + " | y: "+ line[2] + " | y: " + line[3]);
//            System.out.println("================================");
            if ((motorbike.getLeft() > line[0] && motorbike.getLeft() < line[1])
                    || (motorbike.getRight() > line[0] && motorbike.getRight() < line[1])) {
                int top = line[2];
                if (line[2] > line[3]) {
                    top = line[3];
                }
                if (motorbike.getBottom() < top) {
                    int distance = top - motorbike.getBottom();
                    float rate = distance * 100 / laneDistance;
                    if (rate > acceptanceRate) {
                        count++;
                    }
                }
            }
        }
        return count;
    }
}
