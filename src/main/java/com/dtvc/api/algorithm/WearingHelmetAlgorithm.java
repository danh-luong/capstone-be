package com.dtvc.api.algorithm;

import com.dtvc.api.location.MotorbikeLocation;
import com.dtvc.api.location.PersonLocation;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class WearingHelmetAlgorithm {

    public Map<PersonLocation, MotorbikeLocation> isRelatedPersonAndMotorbike(List<MotorbikeLocation> motorbikeLocationList,
                                                                                    List<PersonLocation> personLocationList) throws Exception {
        Map<PersonLocation, MotorbikeLocation> listMap = new HashMap<>();
        for (int i = 0; i < motorbikeLocationList.size(); i++) {
            for (int j = 0; j < personLocationList.size(); j++) {
                if (motorbikeLocationList.get(i).getLeft() < personLocationList.get(j).getLeft()) {
                    if (motorbikeLocationList.get(i).getTop() > personLocationList.get(j).getTop()) {
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() &&
                                motorbikeLocationList.get(i).getRight() < personLocationList.get(j).getRight()) {
                            //vùng diện tích trùng lắp(IoU) có tọa độ là
                            //trên của IoU là trên của xe, trái của IoU là trái của người

                            //phải, trên có sẵn của xe là góc phải trên của IoU

                            //trái, dưới của người là góc trái dưới của IoU

                            //dưới của IoU là dưới của người, phải của IoU là phải của xe

                        }
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() &&
                                motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getRight()) {
                            //vùng diện tích trùng lắp(IoU) có tọa độ là
                            //trái của IoU là với trái của người, trên của IoU là trên của xe

                            //trái, dưới của IoU là trái dưới của người

                            //dưới, phải của IoU là dưới phải của người

                            //phải của IoU là phải của người, trên của IoU là trên của xe

                        }
                    }
                }
                if (motorbikeLocationList.get(i).getLeft() > personLocationList.get(j).getLeft()) {
                    if (motorbikeLocationList.get(i).getTop() > personLocationList.get(j).getTop()) {
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() &&
                                motorbikeLocationList.get(i).getRight() < personLocationList.get(j).getRight()) {
                            //vùng diện tích trùng lắp(IoU) có tọa độ là
                            //trên, trái của IoU là trên, trái của xe

                            //trên, phải của IoU là trên, phải của xe

                            //trái của IoU là trái của xe, dưới của IoU là dưới của người

                            //phải của IoU là phải của xe, dưới của IoU là dưới của người

                        }
                        if (motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getLeft() &&
                                motorbikeLocationList.get(i).getRight() > personLocationList.get(j).getRight()) {
                            //vùng diện tích trùng lắp(IoU) có tọa độ là
                            //trên, trái của IoU là trên, trái của xe

                            //trên của IoU là trên của xe, phải của IoU là phải của người

                            //dưới của IoU là dưới của người, trái của IoU là trái của xe

                            //dưới, phải của IoU là dưới, phải của người

                        }
                    }
                }
                if (motorbikeLocationList.get(i).getLeft() < personLocationList.get(j).getLeft()
                    && motorbikeLocationList.get(i).getRight() < personLocationList.get(j).getRight()
                    && motorbikeLocationList.get(i).getTop() < personLocationList.get(j).getTop()
                    && motorbikeLocationList.get(i).getBottom() < personLocationList.get(j).getBottom()) {
                    //trả về 100% ko tính IoU
                }
            }
        }
        return listMap;
    }
}
