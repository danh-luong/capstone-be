package com.dtvc.api.location;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ObjectLocation {

    private int left;
    private int top;
    private int right;
    private int bottom;

    public double calculateOverlapRate(ObjectLocation object2) {
        if (this.right <= object2.left || this.left >= object2.right)
            return 0;
        if (this.top <= object2.bottom || object2.top <= this.bottom)
            return 0;

        int length = Math.min(this.right, object2.right) - Math.max(this.left, object2.left);
        int width = Math.min(this.bottom, object2.bottom) - Math.max(this.top, object2.top);

        int overlapArea = length * width;
        int minArea = Math.min(this.calculateArea(), object2.calculateArea());

        return overlapArea / minArea;
    }

    private int calculateArea() {
        return (this.right - this.left) * (this.bottom - this.top);
    }

    public int getMinRight(int rightOfObject) {
        if (this.right <= rightOfObject) {
            return this.right;
        } else {
            return rightOfObject;
        }
    }

    public int getMaxLeft(int leftOfObject) {
        if (this.left >= leftOfObject) {
            return this.left;
        } else {
            return leftOfObject;
        }
    }

    public int getMinBottom(int bottomOfObject) {
        if (this.bottom <= bottomOfObject) {
            return this.bottom;
        } else {
            return bottomOfObject;
        }
    }

    public int getMaxTop(int topOfObject) {
        if (this.top >= topOfObject) {
            return this.top;
        } else {
            return topOfObject;
        }
    }
}
