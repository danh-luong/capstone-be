package com.dtvc.api.algorithm;

import com.dtvc.api.location.HelmetLocation;
import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.PersonLocation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WearingHelmetAlgorithm {

    private static final double thresholdPersonMotorbike = 0.5;
    private static final double thresholdHelmetPerson = 0.7;

    public Map<PersonLocation, MotorbikeLocation> isRelatedPersonAndMotorbike(List<MotorbikeLocation> motorbikeLocationList,
                                                                              List<PersonLocation> personLocationList) throws Exception {
        Map<PersonLocation, MotorbikeLocation> listMap = new HashMap<>();
        Map<PersonLocation, Double> rateOfPersonAndMotorbike = new HashMap<>();
        for (int i = 0; i < motorbikeLocationList.size(); i++) {
            for (int j = 0; j < personLocationList.size(); j++) {
                int squareOfMotorbike = (motorbikeLocationList.get(i).getRight() - motorbikeLocationList.get(i).getLeft()) *
                        (motorbikeLocationList.get(i).getBottom() - motorbikeLocationList.get(i).getTop());
                if (motorbikeLocationList.get(i).getLeft() < personLocationList.get(j).getLeft()) {
                    if (motorbikeLocationList.get(i).getTop() > personLocationList.get(j).getTop()) {
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() ||
                                motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getRight()) {
                            int lengthIoU = motorbikeLocationList.get(i).getMinRight(personLocationList.get(j).getRight()) -
                                    motorbikeLocationList.get(i).getMaxLeft(personLocationList.get(j).getLeft());
                            int widthIoU = motorbikeLocationList.get(i).getMinBottom(personLocationList.get(j).getBottom()) -
                                    motorbikeLocationList.get(i).getMaxTop(personLocationList.get(j).getTop());
                            int squareOfIoU = lengthIoU * widthIoU;
                            double rateIoU = 1.0 * squareOfIoU / squareOfMotorbike;
                            if (rateIoU >= WearingHelmetAlgorithm.thresholdPersonMotorbike) {
                                boolean isExist = false;
                                List<PersonLocation> listKeyPersonLocation = new ArrayList<>(listMap.keySet());
                                if (!listKeyPersonLocation.isEmpty()) {
                                    for (int k = 0; k < listKeyPersonLocation.size(); k++) {
                                        if (listKeyPersonLocation.get(k).getId() == personLocationList.get(j).getId()) {
                                            isExist = true;
                                            break;
                                        }
                                    }
                                }
                                if (isExist) {
                                    if (Double.compare(rateOfPersonAndMotorbike.get(personLocationList.get(j)), rateIoU) < 0) {
                                        listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                                    }
                                    break;
                                } else {
                                    listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                                    rateOfPersonAndMotorbike.put(personLocationList.get(j), new Double(rateIoU));
                                    break;
                                }
                            }
                        }
                    }
                }
                if (motorbikeLocationList.get(i).getLeft() > personLocationList.get(j).getLeft()) {
                    if (motorbikeLocationList.get(i).getTop() > personLocationList.get(j).getTop()) {
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getRight() ||
                                motorbikeLocationList.get(i).getRight() < personLocationList.get(j).getRight()) {
                            int lengthIoU = motorbikeLocationList.get(i).getMinRight(personLocationList.get(j).getRight()) -
                                    motorbikeLocationList.get(i).getMaxLeft(personLocationList.get(j).getLeft());
                            int widthIoU = motorbikeLocationList.get(i).getMinBottom(personLocationList.get(j).getBottom()) -
                                    motorbikeLocationList.get(i).getMaxTop(personLocationList.get(j).getTop());
                            int squareOfIoU = lengthIoU * widthIoU;
                            double rateIoU = 1.0 * squareOfIoU / squareOfMotorbike;
                            if (rateIoU >= WearingHelmetAlgorithm.thresholdPersonMotorbike) {
                                boolean isExist = false;
                                List<PersonLocation> listKeyPersonLocation = new ArrayList<>(listMap.keySet());
                                if (!listKeyPersonLocation.isEmpty()) {
                                    for (int k = 0; k < listKeyPersonLocation.size(); k++) {
                                        if (listKeyPersonLocation.get(k).getId() == personLocationList.get(j).getId()) {
                                            isExist = true;
                                            break;
                                        }
                                    }
                                }
                                if (isExist) {
                                    if (Double.compare(rateOfPersonAndMotorbike.get(personLocationList.get(j)), rateIoU) < 0) {
                                        listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                                    }
                                    break;
                                } else {
                                    listMap.put(personLocationList.get(j), motorbikeLocationList.get(i));
                                    rateOfPersonAndMotorbike.put(personLocationList.get(j), new Double(rateIoU));
                                    break;
                                }
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
                    rateOfPersonAndMotorbike.put(personLocationList.get(j), new Double(1));
                    break;
                }
            }
        }
        return listMap;
    }

    public Map<PersonLocation, HelmetLocation> isRelatedPersonAndHelmet(List<PersonLocation> personLocationList,
                                                                        List<HelmetLocation> helmetLocationList) throws Exception {
        Map<PersonLocation, HelmetLocation> listMap = new HashMap<>();
        Map<PersonLocation, Double> rateOfPersonAndHelmet = new HashMap<>();
        for (int i = 0; i < personLocationList.size(); i++) {
            for (int j = 0; j < helmetLocationList.size(); j++) {
                int squareOfMotorbike = (helmetLocationList.get(j).getRight() - helmetLocationList.get(j).getLeft()) *
                        (helmetLocationList.get(j).getBottom() - helmetLocationList.get(j).getTop());
                if (helmetLocationList.get(j).getLeft() >= personLocationList.get(i).getLeft() &&
                        helmetLocationList.get(j).getRight() <= personLocationList.get(i).getRight()) {
                    int lengthIoU = helmetLocationList.get(j).getMinRight(personLocationList.get(i).getRight()) -
                            helmetLocationList.get(j).getMaxLeft(personLocationList.get(i).getLeft());
                    int widthIoU = helmetLocationList.get(j).getMinBottom(personLocationList.get(i).getBottom()) -
                            helmetLocationList.get(j).getMaxTop(personLocationList.get(i).getTop());
                    int squareOfIoU = lengthIoU * widthIoU;
                    double rateIoU = 1.0 * squareOfIoU / squareOfMotorbike;
                    if (rateIoU >= WearingHelmetAlgorithm.thresholdHelmetPerson) {
                        boolean isExist = false;
                        List<PersonLocation> listKeyPersonLocation = new ArrayList<>(listMap.keySet());
                        if (!listKeyPersonLocation.isEmpty()) {
                            for (int k = 0; k < listKeyPersonLocation.size(); k++) {
                                if (listKeyPersonLocation.get(k).getId() == personLocationList.get(i).getId()) {
                                    isExist = true;
                                    break;
                                }
                            }
                        }
                        if (isExist) {
                            if (Double.compare(rateOfPersonAndHelmet.get(personLocationList.get(i)), rateIoU) < 0) {
                                listMap.put(personLocationList.get(i), helmetLocationList.get(j));
                            }
                            break;
                        } else {
                            listMap.put(personLocationList.get(i), helmetLocationList.get(j));
                            rateOfPersonAndHelmet.put(personLocationList.get(i), new Double(rateIoU));
                            break;
                        }
                    }
                }
                if (helmetLocationList.get(j).getLeft() < personLocationList.get(i).getLeft() &&
                        helmetLocationList.get(j).getRight() > personLocationList.get(i).getRight()) {
                    int lengthIoU = helmetLocationList.get(j).getMinRight(personLocationList.get(i).getRight()) -
                            helmetLocationList.get(j).getMaxLeft(personLocationList.get(i).getLeft());
                    int widthIoU = helmetLocationList.get(j).getMinBottom(personLocationList.get(i).getBottom()) -
                            helmetLocationList.get(j).getMaxTop(personLocationList.get(i).getTop());
                    int squareOfIoU = lengthIoU * widthIoU;
                    double rateIoU = 1.0 * squareOfIoU / squareOfMotorbike;
                    if (rateIoU >= WearingHelmetAlgorithm.thresholdHelmetPerson) {
                        boolean isExist = false;
                        List<PersonLocation> listKeyPersonLocation = new ArrayList<>(listMap.keySet());
                        if (!listKeyPersonLocation.isEmpty()) {
                            for (int k = 0; k < listKeyPersonLocation.size(); k++) {
                                if (listKeyPersonLocation.get(k).getId() == personLocationList.get(i).getId()) {
                                    isExist = true;
                                    break;
                                }
                            }
                        }
                        if (isExist) {
                            if (Double.compare(rateOfPersonAndHelmet.get(personLocationList.get(i)), rateIoU) < 0) {
                                listMap.put(personLocationList.get(i), helmetLocationList.get(j));
                            }
                            break;
                        } else {
                            listMap.put(personLocationList.get(i), helmetLocationList.get(j));
                            rateOfPersonAndHelmet.put(personLocationList.get(i), new Double(rateIoU));
                            break;
                        }
                    }
                }
            }
        }
        return listMap;
    }
}
