package com.dtvc.api.algorithm;

import com.dtvc.api.location.HelmetLocation;
import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.PersonLocation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WearingHelmetAlgorithm {

    private static final double thresholdPersonMotorbike = 0.6;
    private static final double thresholdHelmetPerson = 0.7;

    public Map<PersonLocation, MotorbikeLocation> isRelatedPersonAndMotorbike(List<MotorbikeLocation> motorbikeLocationList,
                                                                              List<PersonLocation> personLocationList) throws Exception {
        Map<PersonLocation, MotorbikeLocation> listMap = new HashMap<>();
        for (int i = 0; i < motorbikeLocationList.size(); i++) {
            for (int j = 0; j < personLocationList.size(); j++) {
                int squareOfMotorbike = (motorbikeLocationList.get(i).getRight() - motorbikeLocationList.get(i).getLeft()) *
                        (motorbikeLocationList.get(i).getBottom() - motorbikeLocationList.get(i).getTop());
                if (motorbikeLocationList.get(i).getLeft() < personLocationList.get(j).getLeft()) {
                    if (motorbikeLocationList.get(i).getTop() > personLocationList.get(j).getTop()) {
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() &&
                                motorbikeLocationList.get(i).getRight() < personLocationList.get(j).getRight()) {
                            //vùng diện tích trùng lắp(IoU) có tọa độ là
                            //trên của IoU là trên của xe, trái của IoU là trái của người
                            int topIoU = motorbikeLocationList.get(i).getTop();
                            int leftIoU = personLocationList.get(j).getLeft();
                            //phải, trên có sẵn của xe là góc phải trên của IoU
                            int rightIoU = motorbikeLocationList.get(i).getRight();
                            //trái, dưới của người là góc trái dưới của IoU
                            int bottomIoU = personLocationList.get(i).getBottom();
                            //dưới của IoU là dưới của người, phải của IoU là phải của xe
                            //--------------------------------------------------------------
                            int squareIoU = (rightIoU - leftIoU) * (bottomIoU - topIoU);
                            double rateOverlap = 1.0 * squareIoU / squareOfMotorbike;
                            if (rateOverlap >= WearingHelmetAlgorithm.thresholdPersonMotorbike) {
                                listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                                System.out.println("case 1");
                                break;
                            }
                        }
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() &&
                                motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getRight()) {
                            //vùng diện tích trùng lắp(IoU) có tọa độ là
                            //trái của IoU là với trái của người, trên của IoU là trên của xe
                            int leftIoU = personLocationList.get(j).getLeft();
                            int topIoU = motorbikeLocationList.get(i).getTop();
                            //trái, dưới của IoU là trái dưới của người
                            int bottomIoU = personLocationList.get(j).getLeft();
                            //dưới, phải của IoU là dưới phải của người
                            int rightIoU = personLocationList.get(j).getRight();
                            //phải của IoU là phải của người, trên của IoU là trên của xe
                            //---------------------------------------------------------------
                            int squareIoU = (rightIoU - leftIoU) * (bottomIoU - topIoU);
                            double rateOverlap = 1.0 * squareIoU / squareOfMotorbike;
                            if (rateOverlap >= WearingHelmetAlgorithm.thresholdPersonMotorbike) {
                                listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                                System.out.println("case 2");
                                break;
                            }
                        }
                    }
                }
                if (motorbikeLocationList.get(i).getLeft() > personLocationList.get(j).getLeft()) {
                    if (motorbikeLocationList.get(i).getTop() > personLocationList.get(j).getTop()) {
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() &&
                                motorbikeLocationList.get(i).getRight() < personLocationList.get(j).getRight()) {
                            //vùng diện tích trùng lắp(IoU) có tọa độ là
                            //trên, trái của IoU là trên, trái của xe
                            int topIoU = motorbikeLocationList.get(i).getTop();
                            int leftIoU = motorbikeLocationList.get(i).getLeft();
                            //trên, phải của IoU là trên, phải của xe
                            int rightIoU = motorbikeLocationList.get(i).getRight();
                            //trái của IoU là trái của xe, dưới của IoU là dưới của người
                            int bottomIoU = personLocationList.get(i).getBottom();
                            //phải của IoU là phải của xe, dưới của IoU là dưới của người
                            //---------------------------------------------------------------------
                            int squareIoU = (rightIoU - leftIoU) * (bottomIoU - topIoU);
                            double rateOverlap = 1.0 * squareIoU / squareOfMotorbike;
                            if (rateOverlap >= WearingHelmetAlgorithm.thresholdPersonMotorbike) {
                                listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                                System.out.println("case 3");
                                break;
                            }
                        }
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() &&
                                motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getRight()) {
                            //vùng diện tích trùng lắp(IoU) có tọa độ là
                            //trên, trái của IoU là trên, trái của xe
                            int topIoU = motorbikeLocationList.get(i).getTop();
                            int leftIoU = motorbikeLocationList.get(i).getLeft();
                            //trên của IoU là trên của xe, phải của IoU là phải của người
                            int rightIoU = personLocationList.get(j).getRight();
                            //dưới của IoU là dưới của người, trái của IoU là trái của xe
                            int bottomIoU = personLocationList.get(j).getBottom();
                            //dưới, phải của IoU là dưới, phải của người
                            //-----------------------------------------------------------------------
                            int squareIoU = (rightIoU - leftIoU) * (bottomIoU - topIoU);
                            double rateOverlap = 1.0 * squareIoU / squareOfMotorbike;
                            if (rateOverlap >= WearingHelmetAlgorithm.thresholdPersonMotorbike) {
                                listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                                System.out.println("case 4");
                                break;
                            }
                        }
                    }
                }
                if (motorbikeLocationList.get(i).getLeft() <= personLocationList.get(j).getLeft()
                        && motorbikeLocationList.get(i).getRight() <= personLocationList.get(j).getRight()
                        && motorbikeLocationList.get(i).getTop() <= personLocationList.get(j).getTop()
                        && motorbikeLocationList.get(i).getBottom() <= personLocationList.get(j).getBottom()) {
                    //trả về 100% ko tính IoU
                    listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                    System.out.println("case 5");
                    break;
                }
            }
        }
        return listMap;
    }

    public Map<PersonLocation, HelmetLocation> isRelatedPersonAndHelmet(List<PersonLocation> personLocationList,
                                                                        List<HelmetLocation> helmetLocationList) throws Exception {
        Map<PersonLocation, HelmetLocation> listMap = new HashMap<>();
        for (int i = 0; i < personLocationList.size(); i++) {
            for (int j = 0; j < helmetLocationList.size(); j++) {
                int squareOfMotorbike = (helmetLocationList.get(j).getRight() - helmetLocationList.get(j).getLeft()) *
                        (helmetLocationList.get(j).getBottom() - helmetLocationList.get(j).getTop());
                if (helmetLocationList.get(j).getLeft() >= personLocationList.get(i).getLeft() &&
                        helmetLocationList.get(j).getRight() <= personLocationList.get(i).getRight()) {
                    if (helmetLocationList.get(j).getTop() >= personLocationList.get(i).getTop() &&
                            helmetLocationList.get(j).getBottom() <= personLocationList.get(i).getBottom()) {
                        //trả về match 100%
                        listMap.put(personLocationList.get(i), helmetLocationList.get(j));
                        break;
                    }
                    if (helmetLocationList.get(j).getTop() < personLocationList.get(i).getTop() &&
                            helmetLocationList.get(j).getBottom() < personLocationList.get(i).getBottom()) {
                        //trên của IoU là trên của người, trái của IoU là trái của helmet
                        int topIoU = personLocationList.get(i).getTop();
                        int leftIoU = helmetLocationList.get(j).getLeft();
                        //trên của IoU là trên của người, phải của IoU là phải của helmet
                        int rightIoU = helmetLocationList.get(j).getRight();
                        //dưới, trái của IoU là dưới, trái của helmet
                        int bottomIoU = helmetLocationList.get(j).getBottom();
                        //dưới, phải của IoU là dưới, phải của helmet
                        //-----------------------------------------------------------
                        int squareIoU = (rightIoU - leftIoU) * (bottomIoU - topIoU);
                        double rateOverlap = 1.0 * squareIoU / squareOfMotorbike;
                        if (rateOverlap >= WearingHelmetAlgorithm.thresholdHelmetPerson) {
                            listMap.put(personLocationList.get(i), helmetLocationList.get(j));
                            break;
                        }
                    }
                }
                if (helmetLocationList.get(j).getLeft() < personLocationList.get(i).getLeft() &&
                        helmetLocationList.get(j).getRight() > personLocationList.get(i).getRight()) {
                    //trên, trái của IoU là trên, trái của người
                    int topIoU = personLocationList.get(i).getTop();
                    int leftIoU = personLocationList.get(i).getLeft();
                    //trên, phải của IoU là trên, phải của người
                    int rightIoU = personLocationList.get(i).getRight();
                    //dưới của IoU là dưới của helmet, trái của IoU là trái của người
                    int bottomIoU = helmetLocationList.get(j).getBottom();
                    //dưới của IoU là dưới của helmet, phải của IoU là phải của người
                    //----------------------------------------------------------------
                    int squareIoU = (rightIoU - leftIoU) * (bottomIoU - topIoU);
                    double rateOverlap = 1.0 * squareIoU / squareOfMotorbike;
                    if (rateOverlap >= WearingHelmetAlgorithm.thresholdHelmetPerson) {
                        listMap.put(personLocationList.get(i), helmetLocationList.get(j));
                        break;
                    }
                }
            }
        }
        return listMap;
    }
}
