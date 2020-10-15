package com.dtvc.api.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Component
public class HelmetLocation {

    private int left;
    private int top;
    private int right;
    private int bottom;

}
