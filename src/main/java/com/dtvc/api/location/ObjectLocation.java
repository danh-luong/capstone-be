package com.dtvc.api.location;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class ObjectLocation {

    private int left;
    private int top;
    private int right;
    private int bottom;

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
