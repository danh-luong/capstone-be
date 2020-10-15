package com.dtvc.api.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Component
public class MotorbikeLocation {

    private int left;
    private int top;
    private int right;
    private int bottom;
}
